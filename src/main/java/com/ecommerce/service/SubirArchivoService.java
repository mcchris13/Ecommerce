package com.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubirArchivoService {

    private String folder = "imagenes//";

    public String guadarImagen(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte [] bytes = file.getBytes();
                Path path = Paths.get(folder + file.getOriginalFilename());
                Files.write(path, bytes);
                return file.getOriginalFilename();
            } catch (IOException ex) {
                Logger.getLogger(SubirArchivoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return "default.jpg";
    }
    
    public void eliminarImagen(String nombre) {
        String ruta = "imagenes//";
        File file = new File(ruta + nombre);
        file.delete();
    }
}
