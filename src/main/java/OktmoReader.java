import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoReader {

    public void readPlaces(String fileName, OktmoData oktmoData){
        long startTime = System.nanoTime();
        int lineCount = 0;
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(fileName)
                                , "cp1251")
                        // читаем файл из двоичного потока
                        // в виде текста с нужной кодировкой
                )
        ) {
            String s;
            while ((s = br.readLine()) != null) { // пока readLine() возвращает не null
                lineCount++;
                String[] s1 = s.split(";");
                if(!s1[3].equals("\"000\"")) {
                    //System.out.println(Arrays.toString(s1));
                    for (int i = 0; i < s1.length; i++) {
                        s1[i] = s1[i].replace("\"", "");
                    }
                    long code;
                    String codeString = s1[0] + s1[1] + s1[2] + s1[3] + s1[4] + s1[5];
                //    codeString = codeString.replaceFirst("^0*", "");
                    code = Long.parseLong(codeString);
                    String s2[] = s1[6].split(" ");
                    String status = "";
                    String name = "";
                    boolean reg = false;
                    from:
                    for(int i=0; i < s2.length; i++){
                        if(OktmoReader.validator(s2[i], "([№А-ЯЁ0-9A-Z].*)")){
                            reg = true;
                            for(int k=0; k < i; k++){
                                status = status + s2[k] + " ";
                            }
                            for(int j=i; j < s2.length; j++){
                                name = name + s2[j] + " ";
                            }
                            break from;
                        }
                    }
                    if(reg = false){
                        status = s1[6];
                        name = "Неопределенный";
                    }

                    //String status = s1[6].substring(0,s1[6].indexOf(" "));
                    //String name = s1[6].substring((s1[6].indexOf(" ")+1), s1[6].length());
                    Place place = new Place(code,status.trim(),name.trim());
                    oktmoData.addPlace(place);
                    if (lineCount == 2000) break; // пример частичного чтения первых 20 строк
                    }
            }
        } catch (IOException ex) {
            System.out.println("Reading error in line " + lineCount);
            ex.printStackTrace();
        }
        System.out.println("Время выполнения:" + (System.nanoTime() - startTime));
    }

    public void readPlacesRegex(String fileName, OktmoData oktmoData){
        long startTime = System.nanoTime();
        int lineCount = 0;
        OktmoGroup regionGroup = null;
        OktmoGroup rayonGroup = null;
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(fileName)
                                , "cp1251")
                        // читаем файл из двоичного потока
                        // в виде текста с нужной кодировкой
                )
        )
        {
            String s;
            outer:
            while ((s = br.readLine()) != null) { // пока readLine() возвращает не null
                lineCount++;
                s = s.replace("\"", "");
                Pattern pattern = Pattern.compile("^(\\d{2});" + "(\\d{3});" +
                        "(\\d{3});" + "(\\d{3});" + "(\\d);" + "(\\d);" +
                        "([-/a-zа-я \\.()]*)" + "([№А-ЯЁ0-9A-Z].*)?;" +
                        "[a-zA-ZА-Яа-я ]*;" + ".*;" + "\\d*;" + "\\d*;");
                Matcher matcher = pattern.matcher(s);

                Pattern groupPattern = Pattern.compile("^(\\d{2});" + "(\\d{3});" +
                        "(\\d{3});" + "(\\d{3});" + "(\\d);" + "(\\d);" +
                        "(.*);" + ".*;" + ".*;" + "\\d*;" + "\\d*;");
                if (matcher.find() && (matcher.group(4).equals("000") == false)){
                    long code = Long.parseLong(matcher.group(1) + matcher.group(2) +
                            matcher.group(3) + matcher.group(4));
                    String name;
                    String status = matcher.group(7);
                    if (matcher.groupCount() > 7){
                        name = matcher.group(8);
                        if (name == null) name = "Неопределеный";
                    }else {
                        name = "Неопределенный";
                    }
                    Place place = new Place(code,status.trim(),name.trim());
                    oktmoData.addPlace(place);
                    if (lineCount == 200000)break;
                }
                else {
                    Matcher groupMatcher = groupPattern.matcher(s);
                        Long code;
                        String name;
                        OktmoGroup group;
                        if (groupMatcher.find()) {
                            if (groupMatcher.group(7).startsWith("Населенные пункты, входящие в состав")
                                    | groupMatcher.group(7).startsWith("Городские поселения")
                                    | groupMatcher.group(7).startsWith("Межселенные территории"))
                            {
                                continue outer;
                            }
                            name = groupMatcher.group(7);
                            code = Long.parseLong(groupMatcher.group(1) +
                                    groupMatcher.group(2) +
                                    groupMatcher.group(3) +
                                    groupMatcher.group(4)
                                    //groupMatcher.group(5) +
                                    //groupMatcher.group(6)
                                    );
                            if (groupMatcher.group(3).equals("000")) {
                                if (groupMatcher.group(2).equals("000")) {
                                    group = new OktmoGroup(code, name, Oktmolevel.REGION);
                                    regionGroup = group;
                                } else {
                                    group = new OktmoGroup(code, name, Oktmolevel.RAYON);
                                    if (regionGroup != null) {
                                        regionGroup.addInnerGroups(group);
                                    }
                                    rayonGroup = group;
                                }
                            } else {
                                group = new OktmoGroup(code, name, Oktmolevel.POSELENIE);
                                if (rayonGroup != null) {
                                    rayonGroup.addInnerGroups(group);
                                }
                            }
                            oktmoData.addGroup(group);

                        }
                    }
                }


            }

        catch (IOException ex) {
            System.out.println("Reading error in line " + lineCount);
            ex.printStackTrace();
        }
        System.out.println("Время выполнения с регулярными выражениями:" + (System.nanoTime() - startTime));
    }

    public static boolean validator(String string, String regex){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string);
        return m.matches();
    }



}
