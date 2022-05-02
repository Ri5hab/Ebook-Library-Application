package riscrypt.tiwed.indianebooklibrary4;

public class Model {

    private String Name;
    private  String Imgurl;
    private String Pdfurl;
    private String py;
    private String pag;
    private String pby;

    Model()
    {

    }

    public Model(String name, String imgurl, String pdfurl, String py, String pag, String pby) {
        Name = name;
        Imgurl = imgurl;
        Pdfurl = pdfurl;
        this.py = py;
        this.pag = pag;
        this.pby = pby;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }

    public String getPdfurl() {
        return Pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        Pdfurl = pdfurl;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getPag() {
        return pag;
    }

    public void setPag(String pag) {
        this.pag = pag;
    }

    public String getPby() {
        return pby;
    }

    public void setPby(String pby) {
        this.pby = pby;
    }
}
