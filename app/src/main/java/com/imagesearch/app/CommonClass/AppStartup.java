package com.imagesearch.app.CommonClass;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.imagesearch.app.database.Repository.LabelRepository;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

import static android.os.Environment.getStorageState;

public class AppStartup {
    private LabelRepository labelRepository;
    private ImagesRepository imageRepository;
    private Context context;
    private String ExternalStoragePath = "";
    List<Label> labelList;
    List<Images> ImageList;
    Realm db;

    int i = 0;

    public AppStartup(Context context) {
        db = Realm.getDefaultInstance();
        this.context = context;
        this.labelRepository = new LabelRepository();
        this.imageRepository = new ImagesRepository();

        labelList = new ArrayList<Label>();
        ImageList = new ArrayList<Images>();

        ExternalStoragePath = Environment.getExternalStorageDirectory().getPath();
    }

    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // Checks if a volume containing external storage is available to at least read.
    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    public void run() throws IOException {
        long count = labelRepository.GetCount();
        if (count == 0) {
            setLabels();
        }

        long imagesCount = imageRepository.GetCount();
        if (imagesCount == 0) {
            saveImageDetails();
            imageRepository.Add(ImageList);
        }
    }

    private void saveImageDetails() throws IOException {
        try {
            SaveImages(ExternalStoragePath);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void SaveImages(String directoryPath) throws IOException {
        try {
            File[] files = new File(directoryPath).listFiles(new ImageFileFilter());
            for (File file : files) {
                if (file.isDirectory() && file.listFiles(new ImageFileFilter()).length > 0) {
                    Log.d("Folder Traverse", file.getAbsolutePath());
                    SaveImages(file.getAbsolutePath());
                } else {
                    String extension = FilenameUtils.getExtension(file.getName());
                    if (extension == "" || extension == null) {
                        continue;
                    }

                    Images img = new Images();
                    img.setId(++i);

                    //Read Creation Date time
                    BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class);
                    img.setName(file.getName());
                    img.setExtension(FilenameUtils.getExtension(file.getName()));
                    img.setUriPath(Uri.fromFile(file).toString());
                    img.setFullPath(file.getAbsolutePath());
                    img.setFileSize(file.length());
                    img.setIsDetectionDone(0);
                    img.setImageCreationDate(new Date(attr.creationTime().to(TimeUnit.MILLISECONDS)));
                    this.ImageList.add(img);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setLabels() {
        try {
            List<String> list = new ArrayList<String>();
            list.add("bonfire");
            list.add("comics");
            list.add("himalayan");
            list.add("iceberg");
            list.add("bento");
            list.add("Sink");
            list.add("Toy");
            list.add("Statue");
            list.add("cheeseburger");
            list.add("Tractor");
            list.add("Sled");
            list.add("aquarium");
            list.add("circus");
            list.add("Sitting");
            list.add("beard");
            list.add("bridge");
            list.add("Tights");
            list.add("bird");
            list.add("Rafting");
            list.add("park");
            list.add("factory");
            list.add("graduation");
            list.add("porcelain");
            list.add("twig");
            list.add("petal");
            list.add("cushion");
            list.add("sunglasses");
            list.add("infrastructure");
            list.add("ferris wheel");
            list.add("pomacentridae");
            list.add("wetsuit");
            list.add("shetland sheepdog");
            list.add("brig");
            list.add("watercolor paint");
            list.add("competition");
            list.add("cliff");
            list.add("badminton");
            list.add("safari");
            list.add("bicycle");
            list.add("stadium");
            list.add("boat");
            //list.add("smile");
            list.add("surfboard");
            list.add("fast food");
            list.add("sunset");
            list.add("hot dog");
            list.add("shorts");
            list.add("bus");
            list.add("bullfighting");
            list.add("sky");
            list.add("gerbil");
            list.add("rock");
            list.add("interaction");
            list.add("dress");
            //list.add("toe");
            list.add("bear");
            list.add("eating");
            list.add("tower");
            list.add("brick");
            list.add("junk");
            list.add("person");
            list.add("human");
            list.add("windsurfing");
            list.add("swimwear");
            list.add("roller");
            list.add("camping");
            list.add("playground");
            list.add("bathroom");
            list.add("laugh");
            list.add("balloon");
            list.add("concert");
            list.add("prom");
            list.add("construction");
            list.add("product");
            list.add("reef");
            list.add("picnic");
            list.add("wreath");
            list.add("wheelbarrow");
            list.add("boxer");
            list.add("necklace");
            list.add("bracelet");
            list.add("casino");
            list.add("windshield");
            list.add("stairs");
            list.add("computer");
            list.add("cookware and bakeware");
            list.add("monochrome");
            list.add("chair");
            list.add("poster");
            list.add("bar");
            list.add("shipwreck");
            list.add("pier");
            list.add("community");
            list.add("caving");
            list.add("cave");
            list.add("tie");
            list.add("cabinetry");
            list.add("underwater");
            list.add("clown");
            list.add("nightclub");
            list.add("cycling");
            list.add("comet");
            list.add("mortarboard");
            list.add("track");
            list.add("christmas");
            list.add("church");
            list.add("clock");
            list.add("dude");
            list.add("cattle");
            list.add("jungle");
            list.add("desk");
            list.add("curling");
            list.add("cuisine");
            list.add("cat");
            list.add("juice");
            list.add("couscous");
            list.add("screenshot");
            list.add("crew");
            list.add("skyline");
            list.add("stuffed toy");
            list.add("cookie");
            list.add("tile");
            list.add("hanukkah");
            list.add("crochet");
            list.add("skateboarder");
            list.add("clipper");
            list.add("nail");
            list.add("cola");
            list.add("cutlery");
            list.add("menu");
            list.add("sari");
            list.add("plush");
            list.add("pocket");
            list.add("neon");
            list.add("icicle");
            list.add("pasteles");
            list.add("chain");
            list.add("dance");
            list.add("dune");
            list.add("santa claus");
            list.add("thanksgiving");
            list.add("tuxedo");
            list.add("mouth");
            list.add("desert");
            list.add("dinosaur");
            list.add("mufti");
            list.add("fire");
            list.add("bedroom");
            list.add("goggles");
            list.add("dragon");
            list.add("couch");
            list.add("sledding");
            list.add("cap");
            list.add("whiteboard");
            list.add("hat");
            list.add("gelato");
            list.add("cavalier");
            list.add("beanie");
            list.add("jersey");
            list.add("scarf");
            list.add("vacation");
            list.add("pitch");
            list.add("blackboard");
            list.add("deejay");
            list.add("monument");
            list.add("bumper");
            list.add("longboard");
            list.add("waterfowl");
            list.add("flesh");
            list.add("net");
            list.add("icing");
            list.add("dalmatian");
            list.add("speedboat");
            list.add("trunk");
            list.add("coffee");
            list.add("soccer");
            list.add("ragdoll");
            list.add("food");
            list.add("standing");
            list.add("fiction");
            list.add("fruit");
            list.add("pho");
            list.add("sparkler");
            list.add("presentation");
            list.add("swing");
            list.add("cairn terrier");
            list.add("forest");
            list.add("flag");
            list.add("frigate");
            //list.add("foot");
            list.add("jacket");
            list.add("pillow");
            list.add("bathing");
            list.add("glacier");
            list.add("gymnastics");
            //list.add("ear");
            list.add("flora");
            list.add("shell");
            list.add("grandparent");
            list.add("ruins");
            list.add("eyelash");
            list.add("bunk bed");
            list.add("balance");
            list.add("backpacking");
            list.add("horse");
            list.add("glitter");
            list.add("saucer");
            //list.add("hair");
            list.add("miniature");
            list.add("crowd");
            list.add("curtain");
            list.add("icon");
            list.add("pixie-bob");
            list.add("herd");
            list.add("insect");
            list.add("ice");
            list.add("bangle");
            list.add("flap");
            list.add("jewellery");
            list.add("knitting");
            list.add("centrepiece");
            list.add("outerwear");
            list.add("love");
            list.add("muscle");
            list.add("motorcycle");
            list.add("money");
            list.add("mosque");
            list.add("tableware");
            list.add("ballroom");
            list.add("kayak");
            list.add("leisure");
            list.add("receipt");
            list.add("lake");
            list.add("lighthouse");
            list.add("bridle");
            list.add("leather");
            list.add("horn");
            list.add("strap");
            list.add("lego");
            list.add("scuba diving");
            list.add("leggings");
            list.add("pool");
            list.add("musical instrument");
            list.add("musical");
            list.add("metal");
            list.add("moon");
            list.add("blazer");
            list.add("marriage");
            list.add("mobile phone");
            list.add("militia");
            list.add("tablecloth");
            list.add("party");
            list.add("nebula");
            list.add("news");
            list.add("newspaper");
            list.add("piano");
            list.add("plant");
            list.add("passport");
            list.add("penguin");
            list.add("shikoku");
            list.add("palace");
            list.add("doily");
            list.add("polo");
            list.add("paper");
            list.add("pop music");
            list.add("skiff");
            list.add("pizza");
            list.add("pet");
            list.add("quilting");
            list.add("cage");
            list.add("skateboard");
            list.add("surfing");
            list.add("rugby");
            list.add("lipstick");
            list.add("river");
            list.add("race");
            list.add("rowing");
            list.add("road");
            list.add("running");
            list.add("room");
            list.add("roof");
            list.add("star");
            list.add("sports");
            list.add("shoe");
            list.add("tubing");
            list.add("space");
            list.add("sleep");
            //list.add("skin");
            list.add("swimming");
            list.add("school");
            list.add("sushi");
            list.add("loveseat");
            list.add("superman");
            list.add("cool");
            list.add("skiing");
            list.add("submarine");
            list.add("song");
            list.add("class");
            list.add("skyscraper");
            list.add("volcano");
            list.add("television");
            list.add("rein");
            list.add("tattoo");
            list.add("train");
            list.add("handrail");
            list.add("cup");
            list.add("vehicle");
            list.add("handbag");
            list.add("lampshade");
            list.add("event");
            list.add("wine");
            list.add("wing");
            list.add("wheel");
            list.add("wakeboarding");
            list.add("web page");
            list.add("ranch");
            list.add("fishing");
            list.add("heart");
            list.add("cotton");
            list.add("cappuccino");
            list.add("bread");
            list.add("sand");
            list.add("museum");
            list.add("helicopter");
            list.add("mountain");
            list.add("duck");
            list.add("soil");
            list.add("turtle");
            list.add("crocodile");
            list.add("musician");
            list.add("sneakers");
            list.add("wool");
            list.add("ring");
            list.add("singer");
            list.add("carnival");
            list.add("snowboarding");
            list.add("waterskiing");
            list.add("wall");
            list.add("rocket");
            list.add("countertop");
            list.add("beach");
            list.add("rainbow");
            list.add("branch");
            list.add("moustache");
            list.add("garden");
            list.add("gown");
            list.add("field");
            list.add("dog");
            list.add("superhero");
            list.add("flower");
            list.add("placemat");
            list.add("place");
            list.add("subwoofer");
            list.add("cathedral");
            list.add("building");
            list.add("airplane");
            list.add("fur");
            list.add("bull");
            list.add("bench");
            list.add("temple");
            list.add("butterfly");
            list.add("model");
            list.add("marathon");
            list.add("needlework");
            list.add("kitchen");
            list.add("castle");
            list.add("aurora");
            list.add("larva");
            list.add("racing");
            list.add("airliner");
            list.add("dam");
            list.add("textile");
            list.add("groom");
            list.add("fun");
            list.add("steaming");
            list.add("vegetable");
            list.add("unicycle");
            list.add("jeans");
            list.add("flowerpot");
            list.add("drawer");
            list.add("cake");
            list.add("armrest");
            list.add("aviation");
            list.add("fog");
            list.add("fireworks");
            list.add("farm");
            list.add("seal");
            list.add("shelf");
            list.add("bangs");
            list.add("lightning");
            list.add("van");
            list.add("sphynx");
            list.add("tire");
            list.add("denim");
            list.add("prairie");
            list.add("snorkeling");
            list.add("umbrella");
            list.add("asphalt");
            list.add("sailboat");
            list.add("basset hound");
            list.add("pattern");
            list.add("supper");
            list.add("veil");
            list.add("waterfall");
            list.add("lunch");
            list.add("odometer");
            list.add("baby");
            list.add("glasses");
            list.add("car");
            list.add("aircraft");
            //list.add("hand");
            list.add("rodeo");
            list.add("canyon");
            list.add("meal");
            list.add("softball");
            list.add("alcohol");
            list.add("bride");
            list.add("swamp");
            list.add("pie");
            list.add("bag");
            list.add("joker");
            list.add("supervillain");
            list.add("army");
            list.add("canoe");
            list.add("selfie");
            list.add("rickshaw");
            list.add("barn");
            list.add("archery");
            list.add("aerospaceengineering");
            list.add("storm");
            list.add("helmet");
            list.add("team");
            list.add("fashion good");
            list.add("home good");

            List<Label> labelList = new ArrayList<Label>();
            for (int i = 1; i <= list.size(); i++) {
                Label l = new Label();
                l.setId(i);
                l.setLabelName(list.get(i - 1));
                labelList.add(l);
            }

            labelRepository.Add(labelList);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
