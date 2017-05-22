package transportproject.transportwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transportproject.transportwebsite.dto.FavoriteItem;
import transportproject.transportwebsite.model.transport.Stop;
import transportproject.transportwebsite.service.FavoriteService;

@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> favoriteItem(@RequestBody FavoriteItem item) {
        if (item.getType().equalsIgnoreCase(Stop.class.getSimpleName())) {
            favoriteService.addFavoriteStop(item.getId());
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
