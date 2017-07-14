public class Note{
    public static final double C = 0.10274011814266215;
    public static final double Db = 0.10884936230034181;
    public static final double D = 0.11532187765228541;
    public static final double Eb = 0.12217928719167895;
    public static final double E = 0.1294444656400314;
    public static final double F = 0.13714161307825246;
    public static final double Gb = 0.14529645129619337;
    public static final double G = 0.15393627288003223;
    public static final double Ab = 0.16308979395011822;
    public static final double A = 0.17278759594743864;
    public static final double Bb = 0.1830620765462336;
    public static final double B = 0.1939474987413814;

    public double pitch;
    public int volume;
    public int start;
    public int end;

    public Note(double p, int v, double s, double e){
        pitch = p;
        if(v < 0){
            v = 0;
        }
        else if(v > 127){
            v = 127;
        }
        volume = v;
        if(s < 0){
            s = 0;
        }
        start = (int)(s * 16000);
        if(e < s + 0.125){
            e = s + 0.125;
        }
        end = (int)(e * 16000);
    }

    private static double val(double d){
        return d * Math.PI / 256000;
    }

    private static void getFreqs(){
        System.out.println(val(8372.018));
        System.out.println(val(8869.844));
        System.out.println(val(9397.272));
        System.out.println(val(9956.064));
        System.out.println(val(10548.084));
        System.out.println(val(11175.304));
        System.out.println(val(11839.82));
        System.out.println(val(12543.856));
        System.out.println(val(13289.752));
        System.out.println(val(14080.0));
        System.out.println(val(14917.24));
        System.out.println(val(15804.264));
    }
}