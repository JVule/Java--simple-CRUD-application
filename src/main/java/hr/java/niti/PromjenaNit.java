package hr.java.niti;

import hr.java.entiteti.Serijalizacija;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.List;

public class PromjenaNit implements Runnable{
    private TextArea textArea;

    public PromjenaNit(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void run() {
        Serijalizacija serijalizacija=new Serijalizacija();
        List<String> listPromjene=serijalizacija.deserijaliziraj("dat//djecaSerijalizacija.bin");
        listPromjene.add(serijalizacija.deserijaliziraj("dat//kuhariceSerijalizacija.bin").toString()+"\n");
        listPromjene.add(serijalizacija.deserijaliziraj("dat//odgajateljiSerijalizacija.bin").toString()+"\n");
        listPromjene.add(serijalizacija.deserijaliziraj("dat//roditeljiSerijalizacija.bin").toString()+"\n");
        listPromjene.add(serijalizacija.deserijaliziraj("dat//jeloSerijalizacija.bin").toString()+"\n");
        listPromjene.add(serijalizacija.deserijaliziraj("dat//dolazakSerijalizacija.bin").toString());
        Platform.runLater(()->{
            textArea.setText(listPromjene.toString());
        });

    }
}
