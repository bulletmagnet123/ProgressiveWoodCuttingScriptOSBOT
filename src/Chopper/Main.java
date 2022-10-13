package Chopper;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

@ScriptManifest(name = "Bullets OSBOT Chopper", author = "Bulletmagnet", logo = "", version = 0.1, info = "Woodcutting script")
public class Main extends Script {
    Area DraynorTrees = new Area(3072, 3276, 3088, 3262);
    Area DraynorOak = new Area(3103, 3241, 3098, 3246);
    Area Willows = new Area(3064, 3256, 3056, 3249);
    public int treesChopped = 0;

    private long timeBegan;
    private long timeRan;

    private int beginningXp;
    private int wcLevel;
    PaintAPI paint = new PaintAPI();
    boolean StartScript = false;

    boolean BankorDrop = Boolean.parseBoolean(null);


    public long startTime = 0L, millis = 0L, hours = 0L;


    public void onMessage(Message m) {
        if (m.getMessage().contains("You get some")) {
            treesChopped++;
        }
    }

    public void GetAxesFromGe() {

    }


    public void bank() throws InterruptedException {
        log("Running bank method");
        if (getInventory().contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe")) {
            if (getInventory().isFull()) {
                walking.walk(getBank().closest());
                sleep(random(1400, 2700));
                getBank().open();

                getBank().depositAllExcept("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe");
                sleep(random(1700, 2300));
                getBank().close();
                sleep(random(1700, 2300));
            }
        } else if (!getInventory().contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe")) {
            GetWcEquipment();
        } else {
            chop();
        }
    }

    public void drop() throws InterruptedException {
        log("Running drop method");
        if (BankorDrop == false) {
            if (getInventory().contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe")) {
                if (getInventory().isFull()) {
                    sleep(random(800, 1300));
                    getInventory().dropAllExcept(AxeShouldHave());
                    sleep(random(800, 1300));
                } else {
                    chop();
                }
            } else if (!getInventory().contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe")) {
                GetWcEquipment();
            } else {
                getInventory().dropAllExcept(AxeShouldHave());
            }
        } else {
            bank();
        }

    }

    public void BuyWcEquipment() throws InterruptedException {
        if (getInventory().contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe") && !bank.contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe")) {
            sleep(random(1400, 2700));
            return;
        } else {

        }
    }

    public void ShouldWalkToGE() throws InterruptedException {
        if (!getBank().contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe")) {
            ;
            sleep(random(1400, 2700));
            getWalking().webWalk(Banks.GRAND_EXCHANGE);
        }

    }

    public void HasCash() {

    }


    public void chop() throws InterruptedException {

        Area CHOPHERE = null;
        RS2Object TREE = null;
        int wcLvl = getSkills().getStatic(Skill.WOODCUTTING);
        log("Running Chop Method");


        if (wcLvl <= 14) {
            CHOPHERE = DraynorTrees;
            TREE = getObjects().closest("Tree");
            log("Set CHOPEHERE to Dryanor trees");
        }
        if (wcLvl >= 15) {
            CHOPHERE = DraynorOak;
            TREE = getObjects().closest("Oak");
            log("Set CHOPEHERE to Dryanor OAKS");
        }
        if (wcLvl >= 30) {
            CHOPHERE = Willows;
            TREE = getObjects().closest("Willow");
            log("Set CHOPEHERE to WILLOWS");
        }
        log("Just finished getting: TREELOCATION AND TREE");
        if (getInventory().contains("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe") && !getInventory().isFull()) {
            if (!CHOPHERE.contains(myPlayer())) {
                log("SHOULD START WALKING TO TREE LOCATION.: BEGGGNING OF IF STATMENT :");
                getWalking().webWalk(CHOPHERE.getRandomPosition());
                sleep(random(1200, 2700));
                log("Should be walking to place to cut trees");
            } else if (CHOPHERE.contains(myPlayer())) {
                if (TREE != null && !myPlayer().isMoving() && !myPlayer().isAnimating()) {
                    if (CHOPHERE.contains(TREE)) {
                        TREE.interact("Chop down");
                        sleep(random(1000, 2700));
                        getMouse().moveOutsideScreen();
                        new ConditionalSleep(Script.random(10000, 15000)) {
                            public boolean condition()
                                    throws InterruptedException {
                                return !myPlayer().isAnimating();
                            }
                        }.sleep();
                    }
                }
            }
        } else if (getInventory().isFull()) {
            bank();
        }
    }

    public void GetWcEquipment() throws InterruptedException {
        log("GETTING EQUiPMENT");
        int wcLvl = getSkills().getStatic(Skill.WOODCUTTING);
        String MainAxe = null;
        walking.walk(getBank().closest());
        sleep(random(1400, 2700));
        getBank().open();
        sleep(random(1200, 2700));
        if (getInventory().isFull()) {
            getBank().depositAllExcept("Bronze axe", "Black axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe");
        }
        if (wcLvl <= 5) {
            log("Withdrawing Axe Set To Bronze axe");
            MainAxe = "Bronze axe";
        }
        if (wcLvl >= 6) {
            log("Withdrawing Axe Set To Steel axe");
            MainAxe = "Steel axe";
        }
        if (wcLvl >= 11) {

            log("Withdrawing Axe Set To Black axe");
            MainAxe = "Black axe";
        }
        if (wcLvl >= 21) {
            log("Withdrawing Axe Set To Mithril axe");
            MainAxe = "Mithril axe";
        }
        if (wcLvl >= 31) {
            log("Withdrawing Axe Set To Adamant axe");
            MainAxe = "Adamant axe";
        }
        if (wcLvl >= 41) {
            log("Withdrawing Axe Set To Rune axe");
            MainAxe = "Rune axe";
        }
        if (wcLvl >= 61) {
            log("Withdrawing Axe Set To Dragon axe");
            MainAxe = "Dragon axe";
        }
        walking.walk(getBank().closest());
        sleep(random(1400, 2700));
        getBank().open();
        sleep(random(1200, 2700));
        getBank().depositAll();
        sleep(random(1200, 2700));
        if (!getInventory().contains(MainAxe)) {
            sleep(random(1200, 2700));
            getBank().withdraw(MainAxe, 1);
            sleep(random(1200, 2700));
        } else {
            getBank().close();
        }
        getBank().close();
    }

    @Override
    public void onStart() throws InterruptedException {
        paint.exchangeContext(bot);
        super.onStart();
        getCamera().toTop();
        GUI();
        timeBegan = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        wcLevel = skills.getVirtualLevel(Skill.WOODCUTTING);
        beginningXp = skills.getExperience(Skill.WOODCUTTING);
    }

    public String AxeShouldHave() {
        int wcLvl = getSkills().getStatic(Skill.WOODCUTTING);
        String MainAxe = null;
        if (wcLvl <= 5) {
            log("Withdrawing Axe Set To Bronze axe");
            MainAxe = "Bronze axe";
        }
        if (wcLvl >= 6) {
            log("Withdrawing Axe Set To Steel axe");
            MainAxe = "Steel axe";
        }
        if (wcLvl >= 11) {

            log("Withdrawing Axe Set To Black axe");
            MainAxe = "Black axe";
        }
        if (wcLvl >= 21) {
            log("Withdrawing Axe Set To Mithril axe");
            MainAxe = "Mithril axe";
        }
        if (wcLvl >= 31) {
            log("Withdrawing Axe Set To Adamant axe");
            MainAxe = "Adamant axe";
        }
        if (wcLvl >= 41) {
            log("Withdrawing Axe Set To Rune axe");
            MainAxe = "Rune axe";
        }
        if (wcLvl >= 61) {
            log("Withdrawing Axe Set To Dragon axe");
            MainAxe = "Dragon axe";
        }
        return MainAxe;
    }


    @Override
    public int onLoop() throws InterruptedException {
        String MainAxe = AxeShouldHave();
        if (!getInventory().isFull() && getInventory().contains(MainAxe)) {
            chop();
            if (getInventory().isFull() || !getInventory().contains(MainAxe)) {
                bank();
                GetWcEquipment();
                if (!getInventory().contains(MainAxe)) {
                    GetWcEquipment();
                } else {

                }
            }
        }
        return 678;
    }

    public void GUI() {
        JFrame frame = new JFrame();
        frame.setTitle("BulletsChopper");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 170));
        frame.pack();
        frame.setVisible(true);

        JPanel settingPanel = new JPanel();
        frame.add(settingPanel);


        settingPanel.setLayout(new GridLayout(0, 2));
        JLabel Mode = new JLabel();
        Mode.setText("Bank or Drop");
        settingPanel.add(Mode);
        JComboBox<String> BankorDopBox = new JComboBox<>(new String[]{
                "BANK", "DROP"
        });



        settingPanel.add(BankorDopBox);
        JButton start = new JButton();
        settingPanel.add(start);

        start.setText("Start");
        start.addActionListener(l -> {
            StartScript = true;
            frame.dispose();
        });
        String BoD = (String) BankorDopBox.getSelectedItem();

    }

    public class gui2 {



    }


    @Override
    public void onPaint(Graphics2D g) {

        g.setColor(Color.BLUE);
        for (RS2Object i : objects.getAll()) {
            if (i.getName().equals("Willow") && Willows.contains(i)) {
                paint.drawEntity(g, i, "Willow Tree", true, false, false, true, false, false, true);
            }
        }
        Point mP = getMouse().getPosition();
        g.setColor(Color.RED);
        // Draw a line from top of screen (0), to bottom (500), with mouse x coordinate
        g.drawLine(mP.x, 0, mP.x, 500);

        // Draw a line from left of screen (0), to right (800), with mouse y coordinate
        g.drawLine(0, mP.y, 800, mP.y);

        g.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5);
        g.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);

        g.setColor(new Color(51, 51, 51, 140));
        g.drawRect(10, 250, 300, 400);
        g.fillRect(10, 250, 300, 400);


        g.setColor(Color.CYAN);
        timeRan = System.currentTimeMillis() - this.timeBegan;

        g.drawString("Bullets Progressive Chopper Version: 1.0", 15, 265);
        g.drawString("Starting wc level: " + wcLevel, 130, 330);
        g.drawString("Time ran: " + ft(timeRan), 130, 350);
        g.drawString("Current level: " + getSkills().getStatic(Skill.WOODCUTTING), 130, 375);
        g.drawString("Trees Chopped: " + treesChopped, 130, 425);
        g.drawString("current xp: " + getSkills().getExperience(Skill.WOODCUTTING), 130, 450);
        g.drawString("Should use axe: " + AxeShouldHave(), 120, 475);

    }

    private String ft(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }

}
