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

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            seedFellowships();
            seedAnnouncements();
        };
    }

    private void seedAnnouncements() {
        if (announcementRepository.count() == 0) {
            announcementRepository.save(new Announcement("2025 Year of manifestation", "Embrace the glory of His appearing", "🎉", 1));
            announcementRepository.save(new Announcement("May - Month of \"advancement\"", "Moving forward by the Spirit", "😊", 2));
            announcementRepository.save(new Announcement("Early Morning Prayer", "Join us daily at 5:30 AM", "⏰", 3));
        }
    }

    private void seedFellowships() {
        if (fellowshipRepository.count() == 0) {
            List<Fellowship> fellowships = Arrays.asList(
                new Fellowship("ZOE 1", "PASTOR NANCY MATHEW", "86003 40609", "Online", "Saturdays, 5:00 PM", "A vibrant online community focused on Word-based fellowship."),
                new Fellowship("FAVOR (P)", "BRO TONNIE DAS", "9923759249", "Pune", "Saturdays, 6:00 PM", "Growing together in grace and faith."),
                new Fellowship("PERFECTION (P)", "SIS MAKINA GWARA", "8007744208", "Completely Online", "Fridays, 7:00 PM", "Striving for excellence in the things of God."),
                new Fellowship("ZOE 2", "BRO MANESH", "9711696641", "Virtual (Physical + Online attendees)", "Saturdays, 5:30 PM", "A hybrid cell for physical and virtual attendees."),
                new Fellowship("GODS SEED OF LIGHT-1", "SIS UMA TIMMAYA", "8888976703", "Keshavnagar, BT Kawade road, Magarpatta", "Saturdays, 5:00 PM", "Nurturing the seed of God's Word in our hearts."),
                new Fellowship("REHABOTH", "BRO RAKESH KALE", "98819 88420", "Hadapsar", "Sundays, 4:00 PM", "A place of enlargement and prosperity."),
                new Fellowship("HUIOS", "BRO ABHISHEK BANSODE", "9822670500", "Undri", "Fridays, 7:00 PM", "Sons of God manifesting His glory."),
                new Fellowship("FAVOUR 1", "CELINA BANKAR", "9067926001", "Wanowrie", "Saturdays, 5:00 PM", "Walking in the unmerited favor of God."),
                new Fellowship("ZOE 4", "SIS SHOMA", "85271 39450", "Bhugaon", "Saturdays, 6:00 PM", "Experiencing the life of God in its fullness."),
                new Fellowship("ABLAZE", "SIS JOYCE CHIFITAH", "260 978540835", "Keshavnagar", "Saturdays, 5:30 PM", "Setting our community on fire for Christ."),
                new Fellowship("THE LOGOS", "SIS NANDINI PAUL", "93225 28522", "(Akurdi) Online", "Saturdays, 7:00 PM", "Deep study and meditation on the Word."),
                new Fellowship("LIVING WATER", "SIS STELLA", "", "Goa", "Saturdays, 4:00 PM", "Refreshing the soul with the living water of the Spirit."),
                new Fellowship("ZOE 6", "PASTOR NANCY MATHEW", "86003 40609", "Kharadi", "Saturdays, 5:00 PM", "Spreading the gospel in the heart of Kharadi."),
                new Fellowship("TRUEVINE", "SIS JOANNA EPHREM", "7758991652", "Pune", "Saturdays, 6:00 PM", "Abiding in the Vine to bear much fruit."),
                new Fellowship("NEW BEGINNING", "SIS POONAM TRIBHUVAN", "9373352622", "Undri", "Saturdays, 5:00 PM", "Start your journey of faith with us."),
                new Fellowship("NEW BEGINNING 2", "SIS UJWALA THOMAS", "9890019726", "(Dapodi)", "Saturdays, 5:30 PM", "A community for transformation and growth."),
                new Fellowship("NEW BEGINNING 3", "BRO ELISHA SAMUDRE", "95274 43876", "(Sangvi)", "Saturdays, 6:00 PM", "Building a foundation on Christ."),
                new Fellowship("NEW BEGINNING 4", "SIS PRANALI KUTE", "99220 67798", "(Wagholi)", "Saturdays, 5:00 PM", "New life, new hope, new beginnings."),
                new Fellowship("EXPANSION", "SIS VAISHALI BANSODE", "9172608834", "Pune", "Saturdays, 6:00 PM", "Stretching forth to reach more souls."),
                new Fellowship("COMPLETION", "PST VIJAY BANSODE (PASTOR)", "9011045570", "Pune", "Saturdays, 5:00 PM", "Perfecting the saints for the work of ministry."),
                new Fellowship("VICTORY 1", "SIS PAULINE FERNANDES", "97302 27771", "Shop Vadgaon Sheri", "Saturdays, 6:00 PM", "Walking in victory every day."),
                new Fellowship("DUNAMIS", "BRO MANISH DALVI", "7972721237", "Chandan Nagar", "Fridays, 7:00 PM", "Experiencing the power of the Holy Spirit."),
                new Fellowship("FAITH CELL 1", "APARNA AKOMOLAFE", "88309 62494", "DHOBIGHAT(CAMP) AND KONDHWA", "Saturdays, 5:00 PM", "Living by faith and not by sight."),
                new Fellowship("EXCELLENCE", "BRO PRASHANT BANSODE", "9766745276", "Lonikalbhor, Pune", "Saturdays, 6:00 PM", "A culture of excellence in all we do."),
                new Fellowship("DYNAMIC 1", "BRO ANIKET CHANDANSHIV", "9960504903", "Hadapsar", "Saturdays, 5:00 PM", "A dynamic fellowship of Spirit-filled believers."),
                new Fellowship("DYNAMIC 2", "BRO ANIKET CHANDANSHIV", "9960504903", "Wadgaon sheri", "Saturdays, 5:30 PM", "Active and growing in the things of God."),
                new Fellowship("GENESIS 1", "BRO ROHIT", "8788236598", "Ghorpadi", "Saturdays, 6:00 PM", "Beginning a new era of spiritual dominance."),
                new Fellowship("PEACEFUL", "SIS SONIA", "95797 24504", "Kondhwa", "Saturdays, 5:00 PM", "Experiencing the peace that passes all understanding."),
                new Fellowship("GREAT HARVEST", "SIS RUPALI TAMBE", "8080100958", "Pimpri", "Saturdays, 6:00 PM", "Bringing in the harvest of souls."),
                new Fellowship("REDEMPTION", "PASTOR MILIND TAMBE", "9850856183", "Pimpri", "Fridays, 7:00 PM", "Redeemed by the blood of the Lamb."),
                new Fellowship("THE PROLIFIC", "BR ISHAN", "7030770060", "Pimple Gurav", "Saturdays, 5:00 PM", "Bearing fruit in every season."),
                new Fellowship("RADIANCE 1", "PST PARAG SURYWANSHI", "9890511102", "Pune", "Saturdays, 6:00 PM", "Shining the light of the gospel."),
                new Fellowship("CHARIS 1", "SIS LAM NAIK", "9975677661", "Kondhwa", "Saturdays, 5:30 PM", "Living in the abundance of God's grace."),
                new Fellowship("DUNAMIS 1", "SIS REKHA FERNADES", "8605116220", "Manjari", "Saturdays, 6:00 PM", "Walking in power and authority."),
                new Fellowship("SAMRUDHI CELL 1", "SIS SAVITA KADAM", "83088 72140", "Wabori, Nagar", "Saturdays, 5:00 PM", "Prospering in all areas of life."),
                new Fellowship("GRACE-01", "SIS SAVITA", "8010566833", "Vaiduwadi", "Saturdays, 5:30 PM", "Abounding in grace and love."),
                new Fellowship("MAHIMA-01", "SIS NEHA", "7058613795", "Khole Vasti", "Saturdays, 6:00 PM", "Displaying the glory of God.")
            );

            fellowshipRepository.saveAll(fellowships);
        }
    }
}
