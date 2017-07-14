import javax.sound.sampled.LineUnavailableException;

public class Music extends Sound{
    public Music(Note[] notes) throws LineUnavailableException{
        super();
        int len = 0;
        for(Note n : notes){
            if(n.end > len){
                len = n.end;
            }
        }
        data = new byte[len];
        for(int i = 0; i < len; i++){
            double freq = 0;
            for(Note n : notes){
                if(i >= n.start && i < n.end){
                    if(i < n.start + 999){
                        freq += Math.sin(i * n.pitch) * n.volume
                                * (i - n.start + 1) / 1000;
                    }
                    else if(i > n.end - 1000){
                        freq += Math.sin(i * n.pitch) * n.volume
                                * (n.end - i) / 1000;
                    }
                    else{
                        freq += Math.sin(i * n.pitch) * n.volume;
                    }
                }
            }
            data[i] = (byte)freq;
        }
    }

    public static void main2(String[] args){
        Note[] notes = {
                new Note(Note.C, 30, 0, 0.125),
                new Note(Note.Eb, 34, 0.125, 0.25),
                new Note(Note.Gb, 38, 0.25, 0.375),
                new Note(Note.A, 42, 0.375, 0.5),
                new Note(Note.Eb, 46, 0.5, 0.625),
                new Note(Note.Gb, 50, 0.625, 0.75),
                new Note(Note.A, 54, 0.75, 0.875),
                new Note(Note.C * 2, 58, 0.875, 1),


                new Note(Note.A / 2, 31, 1, 1.125),
                new Note(Note.C, 33, 1.125, 1.25),
                new Note(Note.Eb, 35, 1.25, 1.375),
                new Note(Note.Gb, 37, 1.375, 1.5),
                new Note(Note.C, 39, 1.5, 1.625),
                new Note(Note.Eb, 41, 1.625, 1.75),
                new Note(Note.Gb, 43, 1.75, 1.875),
                new Note(Note.A, 45, 1.875, 2),

                new Note(Note.Eb * 2, 31, 1, 1.125),
                new Note(Note.C * 2, 33, 1.125, 1.25),
                new Note(Note.A, 35, 1.25, 1.375),
                new Note(Note.Gb, 37, 1.375, 1.5),
                new Note(Note.C * 2, 39, 1.5, 1.625),
                new Note(Note.A, 41, 1.625, 1.75),
                new Note(Note.Gb, 43, 1.75, 1.875),
                new Note(Note.Eb, 45, 1.875, 2),


                new Note(Note.Gb / 2, 31, 2, 2.125),
                new Note(Note.A / 2, 33, 2.125, 2.25),
                new Note(Note.C, 34, 2.25, 2.375),
                new Note(Note.Eb, 35, 2.375, 2.5),
                new Note(Note.A / 2, 37, 2.5, 2.625),
                new Note(Note.C, 38, 2.625, 2.75),
                new Note(Note.Eb, 39, 2.75, 2.875),
                new Note(Note.Gb, 41, 2.875, 3),

                new Note(Note.Gb * 2, 31, 2, 2.125),
                new Note(Note.Eb * 2, 33, 2.125, 2.25),
                new Note(Note.C * 2, 34, 2.25, 2.375),
                new Note(Note.A, 35, 2.375, 2.5),
                new Note(Note.Eb * 2, 37, 2.5, 2.625),
                new Note(Note.C * 2, 38, 2.625, 2.75),
                new Note(Note.A, 39, 2.75, 2.875),
                new Note(Note.Gb, 41, 2.875, 3),

                new Note(Note.C, 31, 2, 2.125),
                new Note(Note.Eb, 33, 2.125, 2.25),
                new Note(Note.Gb, 34, 2.25, 2.375),
                new Note(Note.A, 35, 2.375, 2.5),
                new Note(Note.Eb, 37, 2.5, 2.625),
                new Note(Note.Gb, 38, 2.625, 2.75),
                new Note(Note.A, 39, 2.75, 2.875),
                new Note(Note.C * 2, 41, 2.875, 3),


                new Note(Note.G, 42, 3, 3.25),
                new Note(Note.E, 34, 3.25, 3.5),
                new Note(Note.Db, 26, 3.5, 3.75),
                new Note(Note.Bb / 2, 18, 3.75, 4),

                new Note(Note.E, 42, 3, 3.25),
                new Note(Note.Db, 34, 3.25, 3.5),
                new Note(Note.Bb / 2, 26, 3.5, 3.75),
                new Note(Note.G / 2, 18, 3.75, 4),

                new Note(Note.Db * 2, 42, 3, 3.25),
                new Note(Note.Bb, 34, 3.25, 3.5),
                new Note(Note.G, 26, 3.5, 3.75),
                new Note(Note.E, 18, 3.75, 4)};
        try{
            Music m = new Music(notes);
            m.play();
        }
        catch(LineUnavailableException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        Note[] notes = {
                new Note(Note.C, 8, 0, 0.25),
                new Note(Note.Eb, 8, 0, 0.25),
                new Note(Note.G, 8, 0, 0.25),
                new Note(Note.C * 2, 8, 0, 0.25),

                new Note(Note.Bb / 2, 8, 1, 1.25),
                new Note(Note.D, 8, 1, 1.25),
                new Note(Note.F, 8, 1, 1.25),
                new Note(Note.Bb, 8, 1, 1.25),

                new Note(Note.Ab / 2, 8, 2, 2.25),
                new Note(Note.C, 8, 2, 2.25),
                new Note(Note.Eb, 8, 2, 2.25),
                new Note(Note.Ab, 8, 2, 2.25),

                new Note(Note.G / 2, 8, 3, 3.5),
                new Note(Note.B / 2, 8, 3, 3.5),
                new Note(Note.D, 8, 3, 3.5),
                new Note(Note.G, 8, 3, 3.5),

                new Note(Note.B / 2, 8, 3.5, 4),
                new Note(Note.D, 8, 3.5, 4),
                new Note(Note.F, 8, 3.5, 4),
                new Note(Note.B, 8, 3.5, 4),


                new Note(Note.C * 2, 32, 0, 0.125),
                new Note(Note.D * 2, 32, 0.125, 0.25),
                new Note(Note.Eb * 2, 32, 0.25, 0.375),
                new Note(Note.E * 2, 32, 0.375, 0.5),
                new Note(Note.F * 2, 32, 0.5, 0.625),
                new Note(Note.Gb * 2, 32, 0.625, 0.75),
                new Note(Note.G * 2, 32, 0.75, 0.875),
                new Note(Note.Ab * 2, 32, 0.875, 1),

                new Note(Note.G * 2, 32, 1, 1.125),
                new Note(Note.Ab * 2, 32, 1.125, 1.25),
                new Note(Note.G * 2, 32, 1.25, 1.375),
                new Note(Note.Eb * 2, 32, 1.375, 1.5),
                new Note(Note.F * 2, 32, 1.5, 1.625),
                new Note(Note.Eb * 2, 32, 1.625, 1.75),
                new Note(Note.D * 2, 32, 1.75, 1.875),
                new Note(Note.Bb, 32, 1.875, 2),

                new Note(Note.C * 2, 32, 2, 2.125),
                new Note(Note.Eb * 2, 32, 2.125, 2.25),
                new Note(Note.D * 2, 32, 2.25, 2.375),
                new Note(Note.Bb, 32, 2.375, 2.5),
                new Note(Note.Ab, 32, 2.5, 2.625),
                new Note(Note.C * 2, 32, 2.625, 2.75),
                new Note(Note.Bb, 32, 2.75, 2.875),
                new Note(Note.G, 32, 2.875, 3),

                new Note(Note.F, 32, 3, 3.125),
                new Note(Note.Ab, 32, 3.125, 3.25),
                new Note(Note.G, 32, 3.25, 3.375),
                new Note(Note.Eb, 32, 3.375, 3.5),
                new Note(Note.D, 32, 3.5, 3.625),
                new Note(Note.F, 32, 3.625, 3.75),
                new Note(Note.G, 32, 3.75, 3.875),
                new Note(Note.B, 32, 3.875, 4),


                new Note(Note.C * 2, 32, 4, 4.125),
                new Note(Note.Eb * 2, 32, 4.125, 4.25),
                new Note(Note.G * 2, 32, 4.25, 4.375),
                new Note(Note.Ab * 2, 32, 4.375, 4.5),
                new Note(Note.Eb * 2, 32, 4.5, 4.625),
                new Note(Note.Ab * 2, 32, 4.625, 4.75),
                new Note(Note.G * 2, 32, 4.75, 4.875),
                new Note(Note.D * 2, 32, 4.875, 5),

        /*new Note(Note.G * 2, 32, 1, 1.125),
        new Note(Note.Ab * 2, 32, 1.125, 1.25),
        new Note(Note.G * 2, 32, 1.25, 1.375),
        new Note(Note.Eb * 2, 32, 1.375, 1.5),
        new Note(Note.F * 2, 32, 1.5, 1.625),
        new Note(Note.Eb * 2, 32, 1.625, 1.75),
        new Note(Note.D * 2, 32, 1.75, 1.875),
        new Note(Note.Bb, 32, 1.875, 2),

        new Note(Note.C * 2, 32, 2, 2.125),
        new Note(Note.Eb * 2, 32, 2.125, 2.25),
        new Note(Note.D * 2, 32, 2.25, 2.375),
        new Note(Note.Bb, 32, 2.375, 2.5),
        new Note(Note.Ab, 32, 2.5, 2.625),
        new Note(Note.C * 2, 32, 2.625, 2.75),
        new Note(Note.Bb, 32, 2.75, 2.875),
        new Note(Note.G, 32, 2.875, 3),

        new Note(Note.F, 32, 3, 3.125),
        new Note(Note.Ab, 32, 3.125, 3.25),
        new Note(Note.G, 32, 3.25, 3.375),
        new Note(Note.Eb, 32, 3.375, 3.5),
        new Note(Note.D, 32, 3.5, 3.625),
        new Note(Note.F, 32, 3.625, 3.75),
        new Note(Note.G, 32, 3.75, 3.875),
        new Note(Note.B, 32, 3.875, 4),*/
        };
        try{
            Music m = new Music(notes);
            m.play();
        }
        catch(LineUnavailableException ex){
            ex.printStackTrace();
        }
    }
}