package com.christembassy.pune;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private SongRepository songRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            seedFellowships();
            seedAnnouncements();
            seedSongs();
        };
    }

    private void seedSongs() {
        if (songRepository.count() == 0) {
            songRepository.save(createSong("Holy God (Hindi)", "Loveworld Singers", "Worship", "🙌", "1jplsqZHF7O6YVHQU1KxZyuZLR60fdGd_", "10ZtKlhxhMhTRcZb9FYzFlWLRAzp97WsE", "A powerful Hindi worship song declaring the holiness of God."));
            songRepository.save(createSong("All For You", "Loveworld Singers", "Worship", "🎶", "1y3sD0JV1-YeOkwqJJC4K8FE4PrXUl1CB", "1ZML7FTIg9aFGW0f46DXioogtD3M3l1sx", "A song of total surrender and devotion."));
            songRepository.save(createSong("Life Immortality Unveiled", "Loveworld Singers", "Worship", "✨", "1cyaiMoFt6MplOPOzDdzAikO9QRqoN7Cp", "1XduYGN2cq_pIyQc-ehzZgcuJ436qYbN9", "Celebrating the life of God in us."));
            songRepository.save(createSong("Lord You are Good", "Loveworld Singers", "Praise", "⭐", "1h-X6cBmONbpDlgm0gUNnXSeSS3FtCvMO", "145rZHKre6-DbzXkKAxejz62-T58H8t_x", "A joyous song of praise to our good God."));
            songRepository.save(createSong("No God Greater Than You", "Loveworld Singers", "Worship", "🙏", "110QCs_y3MwSPkVh0K18vDKWqezcQeYRj", "1Zca7s4qw2rY-bRLf4l3xRLMpcFBFN58C", "Declaring the supremacy of our God."));
        }
    }

    private Song createSong(String title, String artist, String cat, String icon, String driveId, String videoId, String desc) {
        Song s = new Song();
        s.setTitle(title);
        s.setArtist(artist);
        s.setCategory(cat);
        s.setIcon(icon);
        s.setDriveId(driveId);
        s.setVideoDriveId(videoId);
        s.setDescription(desc);
        s.setLyrics("Lyrics coming soon...");
        s.setTeachingNotes("Focus on the presence of the Holy Spirit while singing.");
        return s;
    }

    private void seedAnnouncements() {
        if (announcementRepository.count() == 0) {
            announcementRepository.save(new Announcement("2025 Year of manifestation", "Embrace the glory of His appearing", "🎉", 1));
            announcementRepository.save(new Announcement("August - Month of \"Progression\"", "Genesis 26:12-13: \"Then Isaac sowed in that land, and reaped in the same year an hundredfold: and the LORD blessed him. And the man waxed great, and went forward, and grew until he became very great.\"", "📈", 2));
            announcementRepository.save(new Announcement("Early Morning Prayer", "Join us daily at 5:30 AM", "⏰", 3));
        }
    }

    private void seedFellowships() {
        fellowshipRepository.deleteAll();
        List<Fellowship> fellowships = Arrays.asList(
                new Fellowship("ZOE 1", "PASTOR NANCY MATHEW", "86003 40609", "Online", "MH", "Pune", "Saturdays, 5:00 PM", "A vibrant online community focused on Word-based fellowship."),
                new Fellowship("FAVOR (P)", "BRO TONNIE DAS", "9923759249", "Pune", "MH", "Pune", "Saturdays, 6:00 PM", "Growing together in grace and faith."),
                new Fellowship("PERFECTION (P)", "SIS MAKINA GWARA", "8007744208", "Completely Online", "MH", "Pune", "Fridays, 7:00 PM", "Striving for excellence in the things of God."),
                new Fellowship("ZOE 2", "BRO MANESH", "9711696641", "Virtual (Physical + Online attendees)", "MH", "Pune", "Saturdays, 5:30 PM", "A hybrid cell for physical and virtual attendees."),
                new Fellowship("GODS SEED OF LIGHT-1", "SIS UMA TIMMAYA", "8888976703", "Keshavnagar, BT Kawade road, Magarpatta", "MH", "Pune", "Saturdays, 5:00 PM", "Nurturing the seed of God's Word in our hearts."),
                new Fellowship("REHABOTH", "BRO RAKESH KALE", "98819 88420", "Hadapsar", "MH", "Pune", "Sundays, 4:00 PM", "A place of enlargement and prosperity."),
                new Fellowship("HUIOS", "BRO ABHISHEK BANSODE", "9822670500", "Undri", "MH", "Pune", "Fridays, 7:00 PM", "Sons of God manifesting His glory."),
                new Fellowship("FAVOUR 1", "CELINA BANKAR", "9067926001", "Wanowrie", "MH", "Pune", "Saturdays, 5:00 PM", "Walking in the unmerited favor of God."),
                new Fellowship("ZOE 4", "SIS SHOMA", "85271 39450", "Bhugaon", "MH", "Pune", "Saturdays, 6:00 PM", "Experiencing the life of God in its fullness."),
                new Fellowship("ABLAZE", "SIS JOYCE CHIFITAH", "260 978540835", "Keshavnagar", "MH", "Pune", "Saturdays, 5:30 PM", "Setting our community on fire for Christ."),
                new Fellowship("THE LOGOS", "SIS NANDINI PAUL", "93225 28522", "(Akurdi) Online", "MH", "Pune", "Saturdays, 7:00 PM", "Deep study and meditation on the Word."),
                new Fellowship("LIVING WATER", "SIS STELLA", "", "Goa", "GA", "Panaji", "Saturdays, 4:00 PM", "Refreshing the soul with the living water of the Spirit."),
                new Fellowship("ZOE 6", "PASTOR NANCY MATHEW", "86003 40609", "Kharadi", "MH", "Pune", "Saturdays, 5:00 PM", "Spreading the gospel in the heart of Kharadi."),
                new Fellowship("TRUEVINE", "SIS JOANNA EPHREM", "7758991652", "Pune", "MH", "Pune", "Saturdays, 6:00 PM", "Abiding in the Vine to bear much fruit."),
                new Fellowship("NEW BEGINNING", "SIS POONAM TRIBHUVAN", "9373352622", "Undri", "MH", "Pune", "Saturdays, 5:00 PM", "Start your journey of faith with us."),
                new Fellowship("NEW BEGINNING 2", "SIS UJWALA THOMAS", "9890019726", "(Dapodi)", "MH", "Pimpri-Chinchwad", "Saturdays, 5:30 PM", "A community for transformation and growth."),
                new Fellowship("NEW BEGINNING 3", "BRO ELISHA SAMUDRE", "95274 43876", "(Sangvi)", "MH", "Pimpri-Chinchwad", "Saturdays, 6:00 PM", "Building a foundation on Christ."),
                new Fellowship("NEW BEGINNING 4", "SIS PRANALI KUTE", "99220 67798", "(Wagholi)", "MH", "Pune", "Saturdays, 5:00 PM", "New life, new hope, new beginnings."),
                new Fellowship("EXPANSION", "SIS VAISHALI BANSODE", "9172608834", "Pune", "MH", "Pune", "Saturdays, 6:00 PM", "Stretching forth to reach more souls."),
                new Fellowship("COMPLETION", "PST VIJAY BANSODE (PASTOR)", "9011045570", "Pune", "MH", "Pune", "Saturdays, 5:00 PM", "Perfecting the saints for the work of ministry."),
                new Fellowship("VICTORY 1", "SIS PAULINE FERNANDES", "97302 27771", "Shop Vadgaon Sheri", "MH", "Pune", "Saturdays, 6:00 PM", "Walking in victory every day."),
                new Fellowship("DUNAMIS", "BRO MANISH DALVI", "7972721237", "Chandan Nagar", "MH", "Pune", "Fridays, 7:00 PM", "Experiencing the power of the Holy Spirit."),
                new Fellowship("FAITH CELL 1", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", "Living by faith and not by sight."),
                new Fellowship("EXCELLENCE", "BRO PRASHANT BANSODE", "9766745276", "Lonikalbhor, Pune", "MH", "Pune", "Saturdays, 6:00 PM", "A culture of excellence in all we do."),
                new Fellowship("DYNAMIC 1", "BRO ANIKET CHANDANSHIV", "9960504903", "Hadapsar", "MH", "Pune", "Saturdays, 5:00 PM", "A dynamic fellowship of Spirit-filled believers."),
                new Fellowship("DYNAMIC 2", "BRO ANIKET CHANDANSHIV", "9960504903", "Wadgaon sheri", "MH", "Pune", "Saturdays, 5:30 PM", "Active and growing in the things of God."),
                new Fellowship("GENESIS 1", "BRO ROHIT", "8788236598", "Ghorpadi", "MH", "Pune", "Saturdays, 6:00 PM", "Beginning a new era of spiritual dominance."),
                new Fellowship("PEACEFUL", "SIS SONIA", "95797 24504", "Kondhwa", "MH", "Pune", "Saturdays, 5:00 PM", "Experiencing the peace that passes all understanding."),
                new Fellowship("GREAT HARVEST", "SIS RUPALI TAMBE", "8080100958", "Pimpri", "MH", "Pimpri-Chinchwad", "Saturdays, 6:00 PM", "Bringing in the harvest of souls."),
                new Fellowship("REDEMPTION", "PASTOR MILIND TAMBE", "9850856183", "Pimpri", "MH", "Pimpri-Chinchwad", "Fridays, 7:00 PM", "Redeemed by the blood of the Lamb."),
                new Fellowship("DECLARATION", "BRO AMOL MUGUMAL", "", "Pimpri", "MH", "Pimpri-Chinchwad", "Saturdays, 6:00 PM", "Declaring the Word of God."),
                new Fellowship("MANISHA SUKHWANI CELL", "SIS MANISHA SUKHWANI", "9021423377", "Pimpri", "MH", "Pimpri-Chinchwad", "Saturdays, 6:00 PM", "Fellowship cell in Pimpri."),
                new Fellowship("THE PROLIFIC", "BR ISHAN", "7030770060", "Pimple Gurav", "MH", "Pimpri-Chinchwad", "Saturdays, 5:00 PM", "Bearing fruit in every season."),
                new Fellowship("RADIANCE 1", "PST PARAG SURYWANSHI", "9890511102", "Pune", "MH", "Pune", "Saturdays, 6:00 PM", "Shining the light of the gospel."),
                new Fellowship("CHARIS 1", "SIS LAM NAIK", "9975677661", "Kondhwa", "MH", "Pune", "Saturdays, 5:30 PM", "Living in the abundance of God's grace."),
                new Fellowship("DUNAMIS 1", "SIS REKHA FERNADES", "8605116220", "Manjari", "MH", "Pune", "Saturdays, 6:00 PM", "Walking in power and authority."),
                new Fellowship("SAMRUDHI CELL 1", "SIS SAVITA KADAM", "83088 72140", "Wabori, Nagar", "MH", "Ahilyanagar", "Saturdays, 5:00 PM", "Prospering in all areas of life."),
                new Fellowship("GRACE-01", "SIS SAVITA", "8010566833", "Vaiduwadi", "MH", "Pune", "Saturdays, 5:30 PM", "Abounding in grace and love."),
                new Fellowship("MAHIMA-01", "SIS NEHA", "7058613795", "Khole Vasti", "MH", "Pune", "Saturdays, 6:00 PM", "Displaying the glory of God."),
                // Newly added cells from image transcription
                new Fellowship("GODS SEED OF LIGHT-2", "SIS UMA TIMMAYA", "8888976703", "Keshavnagar, BT Kawade road, Magarpatta", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GODS SEED OF LIGHT-3", "SIS UMA TIMMAYA", "8888976703", "Keshavnagar, BT Kawade road, Magarpatta", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GODS SEED OF LIGHT-4", "SIS UMA TIMMAYA", "8888976703", "Keshavnagar, BT Kawade road, Magarpatta", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GODS SEED OF LIGHT-5", "SIS UMA TIMMAYA", "8888976703", "Keshavnagar, BT Kawade road, Magarpatta", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("ZOE 3", "BRO RAKESH KALE", "98819 88420", "-", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("ZOE 5", "SIS SHOMA", "85271 39450", "Bhugaon", "MH", "Pune", "Saturdays, 6:00 PM", ""),
                new Fellowship("UNNAMED CELL", "SIS REYNAH", "", "", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("VICTORY 2", "SIS PAULINE FERNANDES", "97302 27771", "Online", "MH", "Pune", "Saturdays, 6:00 PM", ""),
                new Fellowship("VICTORY 3", "SIS PAULINE FERNANDES", "97302 27771", "Online", "MH", "Pune", "Saturdays, 6:00 PM", ""),
                new Fellowship("VICTORY 4", "SIS PAULINE FERNANDES", "97302 27771", "Online", "MH", "Pune", "Saturdays, 6:00 PM", ""),
                new Fellowship("FAITH CELL 2", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 3", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 4", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 5", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 6", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 7", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 8", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 9", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAITH CELL 10", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("DUNAMIS", "BRO JOSHUA BANSODE", "9172608834", "Ghorpadi & Keshavnagar", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("I GOT JOY", "SIS POONAM TRIBHUVAN", "9373352622", "Yerawada", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("SHALOM", "SIS SANGEETA BANKAR", "9021112647", "Pimpri", "MH", "Pimpri-Chinchwad", "Saturdays, 5:00 PM", ""),
                new Fellowship("DYNAMIC 3", "BRO ANIKET CHANDANSHIV", "9960504903", "Online?", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("DYNAMIC 4", "SIS DIPALI", "9960504903", "Wadgaon Sheri", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("DYNAMIC 5", "SIS DIPALI", "9960504903", "Wadgaon Sheri", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("DYNAMIC 6", "SIS TRUPTI", "9960504903", "Hadapsar", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("DYNAMIC 7", "SIS SHEETAL", "9284110679", "Hadapsar", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("MEGA 2", "SIS HIMANI MUKKHAII", "8788236598", "X", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 2", "BRO ROHIT", "8788236598", "New Mulawe", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 3", "BRO ROHIT", "8788236598", "Pimple Gurav", "MH", "Pimpri-Chinchwad", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 4", "BRO ROHIT", "8788236598", "Wagholi", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 5", "BRO ROHIT", "8788236598", "Bhosari", "MH", "Pimpri-Chinchwad", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 6", "BRO ROHIT", "8788236598", "Alandi", "MH", "Pimpri-Chinchwad", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 7", "BRO ROHIT", "8788236598", "Keshv Nagar", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 8", "BRO AVINASH KAMBLE", "8788236598", "Ghorawadi - Wagholi", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 9", "SIS ASMITA", "8788236598", "Near G mail - Manjari", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 10", "BRO SACHIN", "8788236598", "Khadki", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 11", "BRO SACHIN", "8788236598", "Ambegaon", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS 12", "BRO SACHIN", "8788236598", "Wadgaon sheri", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GENESIS TEEVO", "SIS ATHASHREE", "81779 03346", "Shewalewadi", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("REJOICE", "SIS PRISCILLA", "9209608561", "X", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAVOR & FAVOR ALWAYS", "SIS SNEHA RAVI", "", "Keshavnagar", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GREATER GRACE", "SIS ARPNA", "", "Shivneri?", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("DUNAMIS 2", "SIS REKHA FERNADES", "8605116220", "Manjari", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("DUNAMIS 3", "SIS BABITA", "", "Manjari", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("SAMRUDHI CELL 2", "SIS SAVITA KADAM", "83088 72140", "Malegaon, Sangamner", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("SAMRUDHI CELL 3", "SIS SAVITA KADAM", "83088 72140", "Nagar City", "MH", "Ahilyanagar", "Saturdays, 5:00 PM", ""),
                new Fellowship("SAMRUDHI CELL 4", "SIS SAVITA KADAM", "83088 72140", "Takarabad", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GOD'S GRACE", "DCNS VAISHALI FRANCIS", "", "", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("GRACE-02", "SIS SAVITA", "8010566833", "Jaysingpur", "MH", "Kolhapur", "Saturdays, 5:00 PM", ""),
                new Fellowship("GRACE-03", "SIS SAVITA", "8010566833", "Shenoli(karad)", "MH", "Kolhapur", "Saturdays, 5:00 PM", ""),
                new Fellowship("MAHIMA-02", "SIS NEHA", "7058613795", "Jamkhed Vasti", "MH", "Beed", "Saturdays, 5:00 PM", ""),
                new Fellowship("MAHIMA-03", "SIS NEHA", "7058613795", "Toranwade Vasti", "MH", "Beed", "Saturdays, 5:00 PM", ""),
                new Fellowship("MAHIMA-04", "SIS NEHA", "7058613795", "Beed", "MH", "Beed", "Saturdays, 5:00 PM", ""),
                new Fellowship("DIVINE", "SIS SUNANDA SASANE", "8356883011", "Shrirampur", "MH", "Ahilyanagar", "Saturdays, 5:00 PM", ""),
                new Fellowship("PROSPEROUS", "SIS SUMALATHA", "9900268253", "Bangalore (Online)", "KA", "Bengaluru", "Saturdays, 5:00 PM", ""),
                new Fellowship("MULTIPLICATION", "BRO RAJINIKANTH", "9538145553", "Bangalore (Online)", "KA", "Bengaluru", "Saturdays, 5:00 PM", ""),
                new Fellowship("PROLIFIC WISDOM", "PASTOR SANTOSH", "9901735784", "Bangalore (Online)", "KA", "Bengaluru", "Saturdays, 5:00 PM", ""),
                new Fellowship("HIGHER LIFE", "SIS JERUSHA", "9901613648", "Bangalore (Online)", "KA", "Bengaluru", "Saturdays, 5:00 PM", ""),
                new Fellowship("VICTORY", "SIS CHUKEY", "7001713905", "Bangalore (Online)", "KA", "Bengaluru", "Saturdays, 5:00 PM", ""),
                new Fellowship("PHRONESIS CELL 1", "BRO ANDINDILILE", "9266513954", "Deepliving GR, UP", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("PHRONESIS CELL 2", "SIS JACQUELINE", "88265 78474", "", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("PHRONESIS CELL 3", "SIS JEMIMA", "9289735078", "Online", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("PHRONESIS CELL 4", "BRO CALVIN", "9773979934", "Online", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("ABUNDANCE CELL", "SIS SHIDAT", "7630024645", "Casa Grande 1, GR, UP", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("GLORIOUS CELL", "SIS DOREEN", "93116 88738", "", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("GLORY CELL 1", "SIS JACQUELINE", "88265 78474", "Qube Studios Habitech, GR, UP", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("GLORY CELL 2", "SIS RECIDA", "9599223756", "Supertech Upcountry, GR, UP", "UP", "Greater Noida", "Saturdays, 5:00 PM", ""),
                new Fellowship("MIMSHACH CELL 1", "SIS FLORENCE", "", "Vadodara Gujarat", "GJ", "Vadodara", "Saturdays, 5:00 PM", ""),
                new Fellowship("MIMSHACH CELL 2", "BRO WISDOM", "89746 96860", "Faridabad, Delhi", "HR", "Faridabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("MIMSHACH-4", "BRO JOSHUA FOGBAWA", "", "Sai hostel Dehradun Uttarakhand (boys hostel)", "UK", "Dehradun", "Saturdays, 5:00 PM", ""),
                new Fellowship("MIMSHACH-5", "Sis Brenda nambi", "955757635", "auditorium school campus Graphic era university", "UK", "Dehradun", "Saturdays, 5:00 PM", ""),
                new Fellowship("MIMSHACH-6", "SIS Veronica Mulanda", "8360304134", "school campus Graphic Era university", "UK", "Dehradun", "Saturdays, 5:00 PM", ""),
                new Fellowship("MIMSHACH-7", "SIS Sarah Amongi", "", "Graphic Era university campus Graphic", "UK", "Dehradun", "Saturdays, 5:00 PM", ""),
                new Fellowship("PERFECTION", "SIS TSHIDY", "9520604791", "Mother Teresa hostel Dehradun Uttarakhand", "UK", "Dehradun", "Saturdays, 5:00 PM", ""),
                new Fellowship("PHOSTER CELL", "Sis Ruth Kwagala", "9520602684", "Graphic Era university campus", "UK", "Dehradun", "Saturdays, 5:00 PM", ""),
                new Fellowship("IMPACT", "SIS PRISCILLA", "9030760434", "Haneef Colony, New Hafeezpet, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("LIGHT", "SIS MONIKA DEBORAH", "9100462052", "Prem nagar, New hafeezpet, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("ZOE (HYD)", "SIS SHALINI", "7207417714", "Pragathi Nagar, Kukatpally, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("RHEMA", "SIS MERCY", "9030498230", "Prem nagar, New hafeezpet, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("BLOSSOM", "BR JONATHAN", "9866760561", "Prem nagar, New hafeezpet, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("PROLIFIC BELIVERS", "SIS GLORY", "", "Prashant Nagar, Kondapur, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("THE PRAYER", "BR JONATHAN", "9866760561", "Prem nagar, New hafeezpet, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("GATHERING CLOUDS", "BRO CHARLES", "9502422665", "New Hafeezpet, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("THE JOY", "BRO ANDREW", "", "Siddipet, Telangana", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("IGNITING SPIRITS", "SIS GLORY", "", "Prashant Nagar, Kondapur, Hyderabad", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("KABHOSH", "Sis Preeti", "", "Siddipet, Telangana", "TG", "Hyderabad", "Saturdays, 5:00 PM", ""),
                new Fellowship("SHINING FOR CHRIST", "SIS ROSEY", "", "Online", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("NEW CREATION GLORIOUS", "BRO SHALOM RAJ", "7702178204", "Poosalavada", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("VICTORY", "SIS SARAH ROSE", "9701217387", "Siva nagar 2", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("AGAPE", "BRO SUDARSHAN", "7660809006", "Rishaw colony", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("BERACHAH", "BRO ZOE", "8367543025", "Gowrishankar Nagar", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("MIRACLE", "SIS JYOTI", "", "ONLINE CELL MEETING", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("REDEEMERS", "PST SHALOM RAJ (PASTOR)", "", "Chenduvaiah Palli", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("JOSHUA", "BRO LEVI BHARATH KUMAR", "", "Fathima nagar", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("CHARIS", "BRO SHYAM", "8179064055", "Sundariah colony", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("IMPACT (AP)", "SIS SARAH ROSE", "9701217387", "Siva nagar", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("SOPHIA", "SIS ALEKYA", "", "Gudam", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("HIGHLY PROLIFIC", "BRO LEVI BHARATH KUMAR", "", "Sundhariah colony", "MH", "Pune", "Saturdays, 5:00 PM", ""),
                new Fellowship("FAVOR (AP)", "BRO SHYAM", "", "Bhindhala colony", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("ALM", "BRO SUDHA JOSEPH", "", "ALM Colony", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("ZION", "SIS USHA", "", "Fathima nagar", "AP", "Badvel", "Saturdays, 5:00 PM", ""),
                new Fellowship("HENOTES", "SIS DR ANITA LYDIA", "98869 73868", "Karungal", "TN", "Kanyakumari", "Saturdays, 5:00 PM", ""),
                new Fellowship("MERCY", "V.J.OSWALD NEWTON", "9488941999", "ZION NAGAR", "TN", "Kanyakumari", "Saturdays, 5:00 PM", ""),
                new Fellowship("GRACE", "VIJI KUMAR", "96773 42375", "MIDALAM", "TN", "Kanyakumari", "Saturdays, 5:00 PM", ""),
                new Fellowship("RAPHA", "JASBIN CHRISTOPHER", "8526042416", "THISAYANVILAI", "TN", "Kanyakumari", "Saturdays, 5:00 PM", "")
            );

            fellowshipRepository.saveAll(fellowships);
    }
}
