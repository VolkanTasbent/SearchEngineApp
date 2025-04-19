package AramaMotoru;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class AramaMotoruGUI {

    public static void arayuzuBaslat() {
        JFrame frame = new JFrame("Esnek Arama Motoru");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JTextField aramaAlani = new JTextField();
        JButton araButonu = new JButton("Ara");

        // JEditorPane ile HTML destekli sonuç alanı
        JEditorPane sonucAlani = new JEditorPane();
        sonucAlani.setContentType("text/html");
        sonucAlani.setEditable(false);
        sonucAlani.setOpaque(false);

        // Scroll pane'e sar
        JScrollPane scrollPane = new JScrollPane(sonucAlani);
        scrollPane.setPreferredSize(new Dimension(580, 300));

        // API seçim kutusu
        String[] apiSecenekleri = {"Wikipedia", "Google", "DuckDuckGo"};
        JComboBox<String> apiComboBox = new JComboBox<>(apiSecenekleri);

        // Üst panel
        JPanel ustPanel = new JPanel(new BorderLayout(10, 10));
        ustPanel.add(new JLabel("API Seçin:"), BorderLayout.WEST);
        ustPanel.add(apiComboBox, BorderLayout.CENTER);
        ustPanel.add(aramaAlani, BorderLayout.SOUTH);

        // Ana panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(ustPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(araButonu, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Mouse ile linke tıklama
        sonucAlani.addHyperlinkListener(e -> {
            if (e.getEventType().toString().equals("ACTIVATED")) {
                try {
                    Desktop.getDesktop().browse(new URI(e.getURL().toString()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Butona tıklayınca arama yap
        araButonu.addActionListener((ActionEvent e) -> {
            String sorgu = aramaAlani.getText();
            if (!sorgu.isEmpty()) {
                String secilenAPI = (String) apiComboBox.getSelectedItem();
                try {
                    String sonuc = aramaYap(sorgu, secilenAPI);
                    sonucAlani.setText(sonuc);
                } catch (Exception ex) {
                    sonucAlani.setText("<html><body>Hata oluştu: " + ex.getMessage() + "</body></html>");
                }
            } else {
                sonucAlani.setText("<html><body>Lütfen bir kelime girin.</body></html>");
            }
        });
    }

    private static String aramaYap(String query, String api) throws Exception {
        switch (api) {
            case "Wikipedia":
                return "<html><body>" + WikipediaSearcher.search(query) + "</body></html>";
            case "Google":
                return "<html><body>" + GoogleSearch.search(query) + "</body></html>";
            case "DuckDuckGo":
                return "<html><body>" + DuckDuckGoSearcher.search(query) + "</body></html>";
            default:
                return "<html><body>Seçilen API geçersiz!</body></html>";
        }
    }
}
