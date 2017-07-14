import javax.sound.sampled.*;

public class Sound{
    public byte[] data;
    private AudioFormat format;
    private SourceDataLine line;

    public Sound(int len) throws LineUnavailableException{
        if(len < 0){
            len = 0;
        }
        data = new byte[len];
        format = new AudioFormat(16000, 8, 1, true, true);
        line = AudioSystem.getSourceDataLine(format);
        for(int i = 0; i < len; i++){
            data[i] = freq(i);
        }
    }

    public Sound() throws LineUnavailableException{
        format = new AudioFormat(16000, 8, 1, true, true);
        line = AudioSystem.getSourceDataLine(format);
    }

    public final void play() throws LineUnavailableException{
        line.open();
        line.start();
        line.write(data, 0, data.length);
        line.drain();
        line.stop();
        line.close();
    }

    public byte freq(int i){
        return (byte)(Math.sin(Note.C * i) * 32);
    }

    public static void main(String[] args){
        try{
            Sound s = new Sound(16000);
            s.play();
        }
        catch(LineUnavailableException ex){
            ex.printStackTrace();
        }
    }
}