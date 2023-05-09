import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class FullScreenMenu extends JFrame{
    Utility utility=new Utility();

    FullScreenMenu(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        int screenWidth = this.getWidth();
        int screenHeight = this.getHeight();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("fallout");
        utility.setWindowIcon(this);
        JLayeredPane layeredPane=new JLayeredPane();
        this.getContentPane().add(layeredPane);

        ImageIcon imageIcon=new ImageIcon("resources/gif.gif");
        Utility.runThisGif(this,layeredPane,imageIcon);
        utility.setFile("resources/soundtrack(WAV).wav");
        utility.loopSound();

        int gap=30;
        int usernameWidth=400;
        int usernameHeight=80;

        JLabel username=new JLabel(utility.getPlayerName());
        username.setBounds(gap,gap,usernameWidth,usernameHeight);
        username.setFont(new Font("Algerian",Font.BOLD,30));
        username.setBackground(Color.red.darker());
        username.setForeground(Color.WHITE.brighter());
        layeredPane.add(username,Integer.valueOf(1));
        System.out.println(utility.getPlayerName());

        int titleWidth=400;
        int titleHeight=80;

        JLabel title=new JLabel("fallout: the new order");
        title.setBounds(gap+username.getX(),gap+username.getHeight(),titleWidth,titleHeight);
        title.setFont(new Font("Algerian",Font.BOLD,30));
        title.setForeground(Color.YELLOW);
        layeredPane.add(title,Integer.valueOf(2));

        int playButtonWidth=300;
        int playButtonHeight=100;
        int playButtonY=title.getHeight()+title.getY()+gap;
        JButton playButton=new JButton("PLAY");
        playButton.setBounds(gap*2,playButtonY,playButtonWidth,playButtonHeight);
        playButton.setFont(new Font("Stencil",Font.PLAIN,40));
        playButton.setBackground(Color.black);
        playButton.setForeground(Color.RED);
        playButton.setFocusPainted(false);
        playButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        layeredPane.add(playButton,Integer.valueOf(4));

        playButton.addActionListener(e -> {
            getContentPane().removeAll();
            revalidate();
            repaint();
            customizePlayer(screenWidth);

        });

        int exitButtonWidth=250;
        int exitButtonHeight=110;
        int exitButtonY=screenHeight-exitButtonHeight-gap;

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(gap,exitButtonY,exitButtonWidth,exitButtonHeight);
        exitButton.setFont(new Font("Stencil",Font.PLAIN,100));
        exitButton.setBackground(Color.GREEN);
        exitButton.setForeground(Color.ORANGE.darker());
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE,4));
        layeredPane.add(exitButton,Integer.valueOf(3));
        exitButton.addActionListener(e -> System.exit(0));

        this.setVisible(true);
    }
    public void customizePlayer(int screenWidth){
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);

        int gap=20;
        int width=200;
        int height=50;
        int addWidth=100;

        JTextArea chooseYourChampion=new JTextArea("click on a picture to choose your champion");
        chooseYourChampion.setEditable(false);
        chooseYourChampion.setBounds((screenWidth/2)-300,gap,600,100);
        chooseYourChampion.setFont(new Font("Bernard MT Condensed",Font.PLAIN,32));
        chooseYourChampion.setForeground(Color.YELLOW);
        chooseYourChampion.setBackground(null);
        this.add(chooseYourChampion);

        Character drake=Character.drake;
        Character cassandra=Character.cassandra;

        JLabel[][] characterDisplay=new JLabel[2][4];
        int outerCount=0;
        for(Character character:Character.getCharacters()){
            characterDisplay[outerCount][0]=new JLabel(character.name()+" stats: ");
            characterDisplay[outerCount][1]=new JLabel("gender: "+character.gender());
            characterDisplay[outerCount][2]=new JLabel("health points: "+character.healthPoints());
            characterDisplay[outerCount][3]=new JLabel("damage: "+character.damage());
            this.add(characterDisplay[outerCount][0]);
            this.add(characterDisplay[outerCount][1]);
            this.add(characterDisplay[outerCount][2]);
            this.add(characterDisplay[outerCount][3]);
            outerCount++;
        }
        organizeLabelBounds(characterDisplay,screenWidth,width,addWidth,gap,height);

        JLabel[] labels1=new JLabel[characterDisplay[0].length*characterDisplay.length];
        outerCount=0;
        int innerCount=0;
        for(int i=0;i<labels1.length;i++){
            if(innerCount==4){
                outerCount++;
                innerCount=0;
            }
            if(outerCount<2) {
                labels1[i] = characterDisplay[outerCount][innerCount];
                innerCount++;
            }else {
                break;
            }
        }

        int firstLabelSize=26;
        int labelsSize=20;
        for(JLabel label:labels1){
            label.setForeground(Color.ORANGE);
            if(label.getText().equals(drake.name()+" stats: ")|| label.getText().equals(cassandra.name()+" stats: ")){
                label.setFont(new Font("Ariel",Font.PLAIN,firstLabelSize));
            }else {
                label.setFont(new Font("Ariel",Font.PLAIN,labelsSize));
            }
            this.add(label);
        }
        this.getContentPane().setBackground(Color.DARK_GRAY.darker());

        JButton[] profileButtons=new JButton[2];
        profileButtons[0]=new JButton();
        profileButtons[1]=new JButton();
        profileButtons[0].setBounds(characterDisplay[0][0].getX(),gap*10,300,370);
        profileButtons[1].setBounds(characterDisplay[1][0].getX(),gap*10,300,370);
        ImageIcon[] imageIcons=new ImageIcon[profileButtons.length];
       imageIcons[0]=new ImageIcon("resources/drake pic.png");
       imageIcons[1]=new ImageIcon("resources/cassandra.jpg");

       getScaledProfileButtonCovers(imageIcons,profileButtons,this);

        JButton returnButton=new JButton("return");
        returnButton.setBounds(30,940,450,110);
        returnButton.setFont(new Font("Stencil",Font.PLAIN,90));
        returnButton.setBackground(Color.GREEN);
        returnButton.setForeground(Color.ORANGE.darker().darker());
        returnButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE,4));
        this.add(returnButton);
        returnButton.addActionListener(e -> {


        });
        profileButtons[0].addActionListener(e -> {
            Player player=new Player(MainMenu.username,"0",drake);
            startTheGame(utility,player,this);
        });
        profileButtons[1].addActionListener(e -> {
            Player player=new Player(MainMenu.username,"0",cassandra);
            startTheGame(utility,player,this);
        });
    }
    private static void organizeLabelBounds(JLabel[][] characterDisplay, int screenWidth, int width, int addWidth, int gap, int height){
        characterDisplay[0][0].setBounds((screenWidth/4)-(width+addWidth/2),height*12,width+addWidth,height*2);
        characterDisplay[0][1].setBounds(characterDisplay[0][0].getX(),gap+characterDisplay[0][0].getY()+characterDisplay[0][0].getHeight()/2,width,height);
        characterDisplay[0][2].setBounds(characterDisplay[0][1].getX(),gap*2+characterDisplay[0][1].getY(),width,height);
        characterDisplay[0][3].setBounds(characterDisplay[0][2].getX(),gap*2+characterDisplay[0][2].getY(),width,height);

        characterDisplay[1][0].setBounds(screenWidth-characterDisplay[0][0].getX()-characterDisplay[0][0].getWidth(),height*12,width+addWidth,height*2);
        characterDisplay[1][1].setBounds(characterDisplay[1][0].getX(),gap+characterDisplay[1][0].getY()+characterDisplay[1][0].getHeight()/2,width,height);
        characterDisplay[1][2].setBounds(characterDisplay[1][1].getX(),gap*2+characterDisplay[1][1].getY(),width,height);
        characterDisplay[1][3].setBounds(characterDisplay[1][2].getX(),gap*2+characterDisplay[1][2].getY(),width,height);
    }
    private static void getScaledProfileButtonCovers(ImageIcon[] imageIcons,JButton[] profileButtons,JFrame frame){
        for(int i=0;i<imageIcons.length;i++){
            Rectangle rectangle=profileButtons[i].getBounds();
            Image image=imageIcons[i].getImage();
            int imageWidth=rectangle.width;
            int imageHeight=rectangle.height;
            int originalWidth=image.getWidth(null);
            int originalHeight=image.getHeight(null);
            double scaleImage=Math.min((double) imageWidth/originalWidth,(double) imageHeight/originalHeight);
            int newWidth=(int)(originalWidth*scaleImage);
            int newHeight=(int)(originalHeight*scaleImage);
            Image scaled=image.getScaledInstance(newWidth,newHeight,Image.SCALE_SMOOTH);
            ImageIcon buttonCover=new ImageIcon(scaled);
            profileButtons[i].setIcon(buttonCover);
            profileButtons[i].setBorder(BorderFactory.createLineBorder(Color.RED,8));
            profileButtons[i].setFocusPainted(false);
            frame.add(profileButtons[i]);
        }
    }
    private static void startTheGame(Utility utility,Player player, JFrame frame){
        try {
            utility.stopSound();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            new Game(frame,player);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}

