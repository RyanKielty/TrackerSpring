package com.theironyard.controllers;
import com.theironyard.entities.Album;
import com.theironyard.entities.User;
import com.theironyard.services.AlbumRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * Created by ryankielty on 1/27/17.
 */
@Controller
public class AlbumTrackerController {
    @Autowired
    AlbumRepository albums;
    @Autowired
    UserRepository users;
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String albumTitle, String albumArtist, Integer albumReleaseYear) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        if (user != null) {
            model.addAttribute("user", user);
        }

        List<Album> albumList;
        if (albumTitle != null) {
            albumList = albums.findByAlbumTitle(albumTitle);
        } else if (albumArtist != null) {
            albumList = albums.findByAlbumArtist(albumArtist);
        } else if (albumReleaseYear != null) {
            albumList = albums.findByAlbumReleaseYear(albumReleaseYear);
        } else {
            albumList = (List<Album>) albums.findAll();
        }

        model.addAttribute("albums", albumList);
        return "home";
    }
    //login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if(user == null) {
            user = new User(userName, PasswordStorage.createHash(password));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.password)) {
            throw new Exception(("It looks like you forgot your password"));
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }
    //logout
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    //add
    @RequestMapping(path = "/add-album", method = RequestMethod.POST)
    public String addAlbum(HttpSession session, String albumTitle, String albumArtist, int albumReleaseYear) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        Album album = new Album(albumTitle, albumArtist, albumReleaseYear);
        albums.save(album);
        return "redirect:/";
    }
    //edit
    @RequestMapping(path = "/update-album", method = RequestMethod.POST)
    public String editAlbum(HttpSession session, Integer id, String albumTitle, String albumArtist, Integer albumReleaseYear) throws Exception {
        String userName = (String) session.getAttribute("userName");
        Album updatedAlbum = (Album) albums.findOne(id);
        if (id == null) {
            throw new Exception("Need an ID #...");
        }
        if (albumTitle != null || !albumTitle.isEmpty()) {
            updatedAlbum.setAlbumTitle(albumTitle);
        }
        if (albumArtist != null || !albumArtist.isEmpty()) {
            updatedAlbum.setAlbumArtist(albumArtist);
        }
        if (albumReleaseYear != null) {
            updatedAlbum.setAlbumReleaseYear(albumReleaseYear);
        }

        albums.save(updatedAlbum);
        return "redirect:/";
    }
    //delete
    @RequestMapping(path = "remove-album", method = RequestMethod.POST)
    public String removeAlbum(HttpSession session, int id) {
        albums.delete(id);
        return "redirect:/";
    }
    //post construct
    @PostConstruct
    public void init() throws PasswordStorage.CannotPerformOperationException {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Ryan";
            user.password = PasswordStorage.createHash("password");
            users.save(user);
        }
    }
}
