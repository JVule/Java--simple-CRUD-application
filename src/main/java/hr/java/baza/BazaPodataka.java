package hr.java.baza;

import hr.java.entiteti.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.sql.*;
import java.util.stream.Collectors;


public class BazaPodataka {
    private static final Logger logger= LoggerFactory.getLogger(BazaPodataka.class);
    List<Roditelj> listRoditelj;
    List<Dijete> listDijete;
    List<Grupa> listGrupa;
    List<Dolazak> listDolazak;

    public List<Roditelj> getListRoditelj() {
        return listRoditelj;
    }

    public void setListRoditelj(List<Roditelj> listRoditelj) {
        this.listRoditelj = listRoditelj;
    }

    public List<Dijete> getListDijete() {
        return listDijete;
    }

    public void setListDijete(List<Dijete> listDijete) {
        this.listDijete = listDijete;
    }

    private static Connection spojiSeNaBazu() throws IOException,SQLException {
        Properties svojstva=new Properties();
        svojstva.load(new FileReader("dat//bazaSvojstva.properties"));
        Connection connection=DriverManager.getConnection(svojstva.getProperty("bazaUrl"),svojstva.getProperty("korisnik"), svojstva.getProperty("lozinka"));
        return connection;

    }
    public static List<Roditelj> dohvatiRoditelje(){
        List<Roditelj> listaRoditelja=new ArrayList<>();
        try {
            Connection con = spojiSeNaBazu();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM RODITELJ");
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String imeDjeteta = resultSet.getString("ime_djeteta");
                Roditelj noviRoditelj = new Roditelj(id, ime, prezime, imeDjeteta);
                listaRoditelja.add(noviRoditelj);
            }
            statement.close();
         resultSet.close();
        con.close();
        return listaRoditelja;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }

    }
    public static Optional<Roditelj> dohvatiRoditeljPoImenu(String imePrezimeRoditelja){
        Optional<Roditelj>roditeljOptional=Optional.empty();
        Roditelj roditelj=null;
        try {
            Connection con = spojiSeNaBazu();
            for(Roditelj potencijalniRoditelj:dohvatiRoditelje()){
               if(potencijalniRoditelj.toString().toLowerCase().contains(imePrezimeRoditelja.toLowerCase())){
                   roditelj=potencijalniRoditelj;
               }
            }
            roditeljOptional=Optional.ofNullable(roditelj);
            con.close();
            return roditeljOptional;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static void obrisiRoditelja(Roditelj roditelj){
        try {
            Connection con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM RODITELJ WHERE ID=?");
            preparedStatement.setInt(1,roditelj.getId());
            preparedStatement.executeUpdate();

            con.close();
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static  void promjeniRoditelja(Roditelj roditelj){
        try{
            Connection con=spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("UPDATE RODITELJ SET IME=?, PREZIME=?,IME_DJETETA=? WHERE ID=?");
           preparedStatement.setString(1,roditelj.getIme());
           preparedStatement.setString(2, roditelj.getPrezime());
           preparedStatement.setString(3, roditelj.getDijete());
           preparedStatement.setInt(4,roditelj.getId());
            preparedStatement.executeUpdate();
            con.close();

        }catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static void dodajRoditelja(Roditelj roditelj){
        try{
            Connection con=spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO RODITELJ (ime, prezime, ime_djeteta) VALUES ( ?,?,?)");
            preparedStatement.setString(1,roditelj.getIme());
            preparedStatement.setString(2,roditelj.getPrezime());
            preparedStatement.setString(3, roditelj.getDijete());
            preparedStatement.executeUpdate();
            con.close();

        }catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static Roditelj dohvatiRoditeljPoId(Integer id){
        try {
            Connection con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM RODITELJ WHERE ID=?");
            preparedStatement.setLong(1,id);
            ResultSet rs =preparedStatement.executeQuery();
            String ime=null;
            String prezime=null;
            String imeDjeteta=null;
            while(rs.next()){
                id=rs.getInt("id");
                ime=rs.getString("ime");
                prezime=rs.getString("prezime");
                imeDjeteta=rs.getString("ime_djeteta");
            }
            Roditelj roditelj=new Roditelj(id,ime,prezime,imeDjeteta);
            preparedStatement.close();
            con.close();
            return roditelj;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static List<Dijete> dohvatiDjecu(){
        List<Dijete> listaDijete=new ArrayList<>();
        try {
            Connection con = spojiSeNaBazu();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DIJETE");
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                Integer roditeljId=resultSet.getInt("roditelj_id");
                Integer grupaId=resultSet.getInt("grupa_id");
                Integer dob=resultSet.getInt("dob");
                Roditelj roditelj=BazaPodataka.dohvatiRoditeljPoId(roditeljId);
                Grupa grupa=BazaPodataka.dohvatiGrupuPoId(grupaId);
                Dijete dijete=new Dijete(id,ime,prezime,roditelj,dob,grupa);
                listaDijete.add(dijete);
            }
            statement.close();
            resultSet.close();
            con.close();
            return listaDijete;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }

    }
    public static void obrisiDijete(Dijete dijete){
        try {
            Connection con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM DIJETE WHERE ID=?");
            preparedStatement.setInt(1,dijete.getId());
            preparedStatement.executeUpdate();

            con.close();
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
 public static void postaviDijete(Dijete dijete){
     try{
         Connection con=spojiSeNaBazu();
         PreparedStatement preparedStatement=con.prepareStatement("UPDATE DIJETE SET IME=?, PREZIME=?,DOB=?,RODITELJ_ID=?,GRUPA_ID=? WHERE ID=?");
         preparedStatement.setString(1, dijete.getIme());
         preparedStatement.setString(2, dijete.getPrezime());
         preparedStatement.setInt(3,dijete.getDob());
         preparedStatement.setInt(4,dijete.getRoditelj().getId());
         preparedStatement.setInt(5,dijete.getGrupa().getId());
         preparedStatement.setInt(6,dijete.getId());
         preparedStatement.executeUpdate();
         con.close();

     }catch (IOException e) {
         logger.error("greska prilikom čitanja iz properties datotetke");
         throw new RuntimeException(e);
     } catch (SQLException e) {
         logger.error("greska prilikom spajanja na bazu");
         throw new RuntimeException(e);
     }
 }
    public static void dodajDijete(Dijete dijete){
        try{
            Connection con=spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO DIJETE (ID, IME, PREZIME, DOB, RODITELJ_ID, GRUPA_ID) VALUES ( ?,?,?,?,?,?)");
            preparedStatement.setInt(1,dijete.getId());
            preparedStatement.setString(2, dijete.getIme());
            preparedStatement.setString(3, dijete.getPrezime());
            preparedStatement.setInt(4,dijete.getDob());
            preparedStatement.setInt(5,dijete.getRoditelj().getId());
            preparedStatement.setInt(6,dijete.getGrupa().getId());
            preparedStatement.executeUpdate();
            con.close();

        }catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static Dijete dohvatiDijetePoIme(String imePrezimeDijeteta){
        try {
            Connection con = spojiSeNaBazu();
            int idx = imePrezimeDijeteta.lastIndexOf(' ');
            if (idx == -1)
                throw new IllegalArgumentException("samo ime djeteta " + imePrezimeDijeteta);
            String imeDjeteta = imePrezimeDijeteta.substring(0, idx);
            String prezimeDjeteta  = imePrezimeDijeteta.substring(idx + 1);
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM DIJETE WHERE IME=? AND PREZIME=?");
            preparedStatement.setString(1,imeDjeteta);
            preparedStatement.setString(2,prezimeDjeteta);
            ResultSet rs =preparedStatement.executeQuery();
            Integer id=null;
            Integer roditelj_id=null;
            Integer grupaId=0;
            String ime=null;
            String prezime=null;
            Roditelj roditelj=null;
            Integer dob=null;
            Grupa grupa=null;
            while(rs.next()){
                id=rs.getInt("id");
                ime=rs.getString("ime");
                prezime=rs.getString("prezime");
                roditelj_id=rs.getInt("roditelj_id");
                grupaId=rs.getInt("grupa_id");
                dob=rs.getInt("dob");
            }
            grupa=BazaPodataka.dohvatiGrupuPoId(grupaId);
            roditelj=BazaPodataka.dohvatiRoditeljPoId(roditelj_id);
            Dijete dijete=new Dijete(id,ime,prezime,roditelj,dob,grupa);
            preparedStatement.close();
            con.close();
            return dijete;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static Dijete dohvatiDijetePoId(Integer id){
        try {
            Connection con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM DIJETE WHERE ID=?");
            preparedStatement.setInt(1,id);
            ResultSet rs =preparedStatement.executeQuery();
            String ime=null;
            String prezime=null;
            Roditelj roditelj=null;
            Grupa grupa=null;
            Integer dob =null;
            while(rs.next()){
                id=rs.getInt("id");
                ime=rs.getString("ime");
                prezime=rs.getString("prezime");
                 dob=rs.getInt("dob");
                Integer roditeljId= rs.getInt("roditelj_id");
                Integer grupaId=rs.getInt("grupa_id");
                roditelj=BazaPodataka.dohvatiRoditeljPoId(roditeljId);
                grupa=BazaPodataka.dohvatiGrupuPoId(grupaId);

            }
            Dijete dijete=new Dijete(id,ime,prezime,roditelj,dob,grupa);
            preparedStatement.close();
            con.close();
            return dijete;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static List<Grupa> dohvatiGrupe(){
        List<Grupa> listaGrupa= new ArrayList<>();
        try {
            Connection con = spojiSeNaBazu();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM GRUPA");
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String naziv = resultSet.getString("naziv");
               Grupa grupa=new Grupa(id,naziv);
                listaGrupa.add(grupa);
            }
            statement.close();
            resultSet.close();
            con.close();
            return listaGrupa;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static Optional<Grupa> dohvatiGrupuPoImenu(String imeGrupe)  {
        Connection con=null;
        try {
             con = spojiSeNaBazu();
            Grupa grupa=null;
            List<Grupa> grupaList=dohvatiGrupe();
            for(Grupa potencijalneGrupe:grupaList){
                if(potencijalneGrupe.getNaziv().toLowerCase().contains(imeGrupe.toLowerCase())){
                    grupa=potencijalneGrupe;
                }
            }
            if(grupa == null){
                return Optional.empty();

            }
            else{
                return Optional.of(grupa);
            }
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Grupa dohvatiGrupuPoId(Integer id){
        try {
            Connection con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM GRUPA WHERE ID=?");
            preparedStatement.setLong(1,id);
            ResultSet rs =preparedStatement.executeQuery();
            String nazivGrupe=null;
            while(rs.next()){
                id=rs.getInt("id");
                nazivGrupe= rs.getString("naziv");
            }
            Grupa grupa=new Grupa(id,nazivGrupe);
            preparedStatement.close();
            con.close();
            return grupa;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static List<Odgajatelj> dohvatiOdgajatelje(){
        List<Odgajatelj> listOdgajatelj= new ArrayList<>();
        try {
            Connection con = spojiSeNaBazu();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ODGAJATELJ");
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime=resultSet.getString("prezime");
                Integer idGrupe=resultSet.getInt("id_grupe");
                Grupa grupa=BazaPodataka.dohvatiGrupuPoId(idGrupe);
                Integer placa= resultSet.getInt("placa");
                Odgajatelj odgajatelj=new Odgajatelj(id,ime,prezime,grupa,placa);

                listOdgajatelj.add(odgajatelj);
            }
            statement.close();
            resultSet.close();
            con.close();
            return listOdgajatelj;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static void dodajOdgajatelja(Odgajatelj odgajatelj){
        try{
            Connection con=spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO  ODGAJATELJ (ID,IME,PREZIME,ID_GRUPE,PLACA) VALUES ( ?,?,?,?,? )");
            preparedStatement.setInt(1,odgajatelj.getId());
            preparedStatement.setString(2,odgajatelj.getIme());
            preparedStatement.setString(3,odgajatelj.getPrezime());
            preparedStatement.setInt(4,odgajatelj.getGrupa().getId());
            preparedStatement.setInt(5,odgajatelj.getPlaca());

            preparedStatement.executeUpdate();
            con.close();

        }catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static void promjeniOdgajatelja(Odgajatelj odgajatelj){
        try{
            Connection con=spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("UPDATE ODGAJATELJ SET IME=?, PREZIME=?,ID_GRUPE=?,PLACA=? WHERE ID=?");
            preparedStatement.setString(1,odgajatelj.getIme());
            preparedStatement.setString(2, odgajatelj.getPrezime());
            preparedStatement.setInt(3,odgajatelj.getGrupa().getId());
            preparedStatement.setInt(4,odgajatelj.getPlaca());
            preparedStatement.setInt(5,odgajatelj.getId());

            preparedStatement.executeUpdate();
            con.close();

        }catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static void obrisiOdgajatelja(Odgajatelj odgajatelj){
        try {
            Connection con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM ODGAJATELJ WHERE ID=?");
            preparedStatement.setInt(1,odgajatelj.getId());
            preparedStatement.executeUpdate();

            con.close();
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static Odgajatelj dohvatiOdgajateljPoId(Integer id){
        try {
            Connection con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM ODGAJATELJ WHERE ID=?");
            preparedStatement.setInt(1,id);
            ResultSet rs =preparedStatement.executeQuery();
            String ime=null;
            String prezime=null;
            Integer idGrupe= 0;
            Integer placa=0;
            Grupa grupa=null;
            while(rs.next()){
                id=rs.getInt("id");
                ime=rs.getString("ime");
                prezime=rs.getString("prezime");
                idGrupe=rs.getInt("id_grupe");
                grupa=BazaPodataka.dohvatiGrupuPoId(idGrupe);
                placa=rs.getInt("placa");
            }
            Odgajatelj odgajatelj=new Odgajatelj(id,ime,prezime,grupa,placa);
            preparedStatement.close();
            con.close();
            return odgajatelj;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
    }
    public static void dodajOcjenuOdgajatelju(Odgajatelj odgajatelj,Integer ocjena){
        Connection con=null;
        try {
             con = spojiSeNaBazu();
            PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO OCJENA_RADA_ODGAJATELJ (odgajatelj_id, ocjena) VALUES (?,?)");
            preparedStatement.setInt(1,odgajatelj.getId());
            preparedStatement.setInt(2,ocjena);
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
        public static Odgajatelj dohvatiOdgajateljaPoGrupi(String imeGrupe){
        Odgajatelj odgajatelj=null;
        try {
                Connection con = spojiSeNaBazu();
                List<Odgajatelj> OdgajateljList=dohvatiOdgajatelje();
                for(Odgajatelj potencijalniOdgajatelj:OdgajateljList){
                    if(potencijalniOdgajatelj.getGrupa().getNaziv().toLowerCase().contains(imeGrupe.toLowerCase())){
                       odgajatelj=potencijalniOdgajatelj;
                    }
                }
            } catch (IOException e) {
                logger.error("greska prilikom čitanja iz properties datotetke");
                throw new RuntimeException(e);
            } catch (SQLException e) {
                logger.error("greska prilikom spajanja na bazu");
                throw new RuntimeException(e);
            }
        return  odgajatelj;
        }

    public static List<Dolazak> dohvatiDolaske(){
        List<Dolazak> listaDolazak= new ArrayList<>();
        Connection con=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try {
             con = spojiSeNaBazu();
             statement = con.createStatement();
             resultSet = statement.executeQuery("SELECT * FROM DOLAZAK");
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String naziv = resultSet.getString("naziv_grupe");
                Integer roditeljId=resultSet.getInt("roditelj_id");
                Integer dijeteId=resultSet.getInt("dijete_id");
                Integer odgajateljId=resultSet.getInt("odgajatelj_id");
                Roditelj roditelj=BazaPodataka.dohvatiRoditeljPoId(roditeljId);
                Dijete dijete=BazaPodataka.dohvatiDijetePoId(dijeteId);
                Odgajatelj odgajatelj=dohvatiOdgajateljPoId(odgajateljId);
                LocalDateTime vrijemeDolaska= resultSet.getTimestamp("vrijeme_dolaska").toLocalDateTime();
                Dolazak dolazak=new Dolazak(id,roditelj,dijete,odgajatelj,vrijemeDolaska,naziv);
                listaDolazak.add(dolazak);
            }

            return listaDolazak;
        } catch (IOException e) {
            logger.error("greska prilikom čitanja iz properties datotetke");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("greska prilikom spajanja na bazu");
            throw new RuntimeException(e);
        }
        finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
   public static void dodajDolazak(Dolazak dolazak){
       try {
           Connection con = spojiSeNaBazu();
         PreparedStatement preparedStatement= con.prepareStatement("INSERT INTO DOLAZAK (id, roditelj_id, dijete_id, odgajatelj_id,VRIJEME_DOLASKA, naziv_grupe) VALUES (?,?,?,?,?,?) ");
         preparedStatement.setInt(1,dolazak.getId());
         preparedStatement.setInt(2,dolazak.getRoditelj().getId());
         preparedStatement.setInt(3,dolazak.getDijete().getId());
         preparedStatement.setInt(4,dolazak.getOdgajatelj().getId());
         preparedStatement.setTimestamp(5,Timestamp.valueOf(dolazak.getVrijemeDolaska()));
         preparedStatement.setString(6,dolazak.getNazivGrupe());
         preparedStatement.executeUpdate();
         preparedStatement.close();
           con.close();

       } catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static List<Kuharica> dohvatiKuharice(){
       List<Kuharica> listaKuharica= new ArrayList<>();
       try {
           Connection con = spojiSeNaBazu();
           Statement statement = con.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM KUHARICA");
           while (resultSet.next()) {
               Integer id = resultSet.getInt("id");
               String ime = resultSet.getString("ime");
               String prezime=resultSet.getString("prezime");
               Integer placa=resultSet.getInt("placa");
               String smjenaString=resultSet.getString("smjena");
               Smjena smjena=dohvatiSmjenu(smjenaString);
               Integer jeloId=resultSet.getInt("jelo_id");
               Jelo jelo=dohvatiJeloId(jeloId);
               Kuharica kuharica=new Kuharica(id,ime,prezime,placa,smjena,jelo);
               listaKuharica.add(kuharica);
           }
           resultSet.close();
           statement.close();
           con.close();
           return listaKuharica;


       } catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static void promjeniKuharice(Kuharica kuharica){
       try{
           Connection con=spojiSeNaBazu();
           PreparedStatement preparedStatement=con.prepareStatement("UPDATE KUHARICA SET IME=?, PREZIME=?,JELO_ID=?,PLACA=?,SMJENA=? WHERE ID=?");
           preparedStatement.setString(1,kuharica.getIme());
           preparedStatement.setString(2,kuharica.getPrezime());
           preparedStatement.setInt(3,kuharica.getJelo().getId());
           preparedStatement.setInt(4,kuharica.getPlaca());
           preparedStatement.setString(5,kuharica.getSmjena().toString());
           preparedStatement.setInt(6,kuharica.getId());
           preparedStatement.executeUpdate();
           con.close();

       }catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static void dodajKuharicu(Kuharica kuharica){

       try {
           Connection con = spojiSeNaBazu();
           PreparedStatement preparedStatement= con.prepareStatement("INSERT INTO KUHARICA (id,ime, prezime, placa, smjena, jelo_id) VALUES (?,?,?,?,?,?) ");
          preparedStatement.setInt(1,kuharica.getId());
          preparedStatement.setString(2, kuharica.getIme());
          preparedStatement.setString(3, kuharica.getPrezime());
          preparedStatement.setInt(4,kuharica.getPlaca());
          preparedStatement.setString(5,kuharica.getSmjena().toString());
          preparedStatement.setInt(6,kuharica.getJelo().getId());
           preparedStatement.executeUpdate();
           preparedStatement.close();
           con.close();

       } catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static void obrisiKuharica(Kuharica kuharica){
       try {
           Connection con = spojiSeNaBazu();
           PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM KUHARICA WHERE ID=?");
           preparedStatement.setInt(1,kuharica.getId());
           preparedStatement.executeUpdate();

           con.close();
       } catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static List<Jelo> dohvatiJelo(){
       List<Jelo> listaJela= new ArrayList<>();
       try {
           Connection con = spojiSeNaBazu();
           Statement statement = con.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM JELO");
           while (resultSet.next()) {
               Integer id = resultSet.getInt("id");
               String naziv=resultSet.getString("naziv");
               Integer masti=resultSet.getInt("masti");
               Integer proteini=resultSet.getInt("proteini");
               Integer ugljikohidrati=resultSet.getInt("ugljikohidrati");
               Jelo jelo=new Jelo.Builder(id,naziv,new EnergetskiPodaci(100.0,1000.0)).ugljikohidrati(ugljikohidrati).masti(masti).proteini(proteini).build();
               listaJela.add(jelo);
           }
           statement.close();
           resultSet.close();
           con.close();
           return listaJela;
       } catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static Jelo dohvatiJeloId(Integer id){
        Jelo jelo=null;
       try {
           Connection con = spojiSeNaBazu();
           for(Jelo potencijalnoJelo:dohvatiJelo()){
               if(potencijalnoJelo.getId().compareTo(id)==0){
                   jelo=potencijalnoJelo;
               }
           }

           con.close();
       }
       catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
       return jelo;
   }
   public static void obrisiJelo(Jelo jelo){

       try {
           Connection con = spojiSeNaBazu();
           PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM JELO WHERE ID=?");
           preparedStatement.setInt(1,jelo.getId());
           preparedStatement.executeUpdate();
            preparedStatement.close();
           con.close();
       } catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static void promjeniJelo(Jelo jelo){
       try{
           Connection con=spojiSeNaBazu();
           PreparedStatement preparedStatement=con.prepareStatement("UPDATE JELO SET NAZIV=?,MASTI=?,PROTEINI=?,UGLJIKOHIDRATI=? WHERE ID=?");
           preparedStatement.setString(1,jelo.getNaziv());
           preparedStatement.setInt(2,jelo.getMasti());
           preparedStatement.setInt(3,jelo.getProteini());
           preparedStatement.setInt(4,jelo.getUgljikohidrati());
           preparedStatement.setInt(5,jelo.getId());
           preparedStatement.executeUpdate();
           preparedStatement.close();
           con.close();

       }catch (IOException e) {
           logger.error("greska prilikom čitanja iz properties datotetke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greska prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   public static void dodajJelo(Jelo jelo){
       try {
           Connection connection=spojiSeNaBazu();
           PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO JELO (ID,NAZIV,MASTI,PROTEINI, UGLJIKOHIDRATI) VALUES ( ? ,?, ?,?,? ) ");
           preparedStatement.setInt(1,jelo.getId());
           preparedStatement.setString(2,jelo.getNaziv());
           preparedStatement.setInt(3,jelo.getMasti());
           preparedStatement.setInt(4,jelo.getProteini());
           preparedStatement.setInt(5,jelo.getUgljikohidrati());
           preparedStatement.executeUpdate();
           preparedStatement.close();
           connection.close();
       } catch (IOException e) {
           logger.error("greška prilikom čitanja iz propreties datoeteke");
           throw new RuntimeException(e);
       } catch (SQLException e) {
           logger.error("greška prilikom spajanja na bazu");
           throw new RuntimeException(e);
       }
   }
   private static Smjena dohvatiSmjenu(String naziv){
        if(naziv.compareToIgnoreCase("popodne")==0)
            return  Smjena.POPODNE;
        if(naziv.compareToIgnoreCase("ujutro")==0)
            return Smjena.UJUTRO;
        else{
            return Smjena.DEFAULT;
        }
   }

}
