package hr.java.entiteti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Serijalizacija<T>  {
    private final Logger logger= LoggerFactory.getLogger(Serijalizacija.class);
    T listObjekata;
    public void serijaliziraj(List<T> listaObjekata, String pathString){
        Path path=Paths.get(pathString);
        File file=null;

        try (ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(new File(pathString))))
        {
objectOutputStream.writeObject(listaObjekata);
System.out.println("objekti uspjesno serijalizirani");
        } catch (FileNotFoundException e) {
            logger.error("serijalizacija neuspješna");
            System.out.println("serijalizacija neuspješna");
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("serijalizacija neuspješna");
            System.out.println("serijalizacija neuspješna");
            throw new RuntimeException(e);
        }
    }
    public List<T> deserijaliziraj(String pathName){
        List<T> listaObjekata=new ArrayList<>();
        File file=new File(pathName);
        if(file.length() == 0){
            System.out.println("file je prazan vracam praznu listu objekata");
            return listaObjekata;
        }
        else{
            try(ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(file))){
                listaObjekata=(List<T>)objectInputStream.readObject();
                System.out.println("Uspjesno deserijaliziran objekt");
            } catch (FileNotFoundException e) {
                logger.error("neuspjesna deserijalizacija");
                System.out.println("neuspjesna deserijalizacija");
                throw new RuntimeException(e);
            } catch (IOException e) {
                logger.error("neuspjesna deserilizacija");
                System.out.println("neuspješna deserilizacija");
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return listaObjekata;
    }
}
