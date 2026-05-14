package com.shilpakala.repository

import com.shilpakala.data.model.Artwork
import com.shilpakala.data.model.HeritageSection
import com.shilpakala.data.model.TimelineStage

object MockData {
    // ── Reliable Unsplash sculpture / art images ─────────────────────────────
    private fun img(i: Int) = when (i % 12) {
        0  -> "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800&q=80"
        1  -> "https://images.unsplash.com/photo-1568195160819-2e8ef0563b88?w=800&q=80"
        2  -> "https://images.unsplash.com/photo-1583847268964-b28dc8f51f92?w=800&q=80"
        3  -> "https://images.unsplash.com/photo-1592494260081-d3fb94e012ba?w=800&q=80"
        4  -> "https://images.unsplash.com/photo-1580136579312-94651dfd596d?w=800&q=80"
        5  -> "https://images.unsplash.com/photo-1598887141926-29e00d5dc48a?w=800&q=80"
        6  -> "https://images.unsplash.com/photo-1555685812-4b943f1cb0eb?w=800&q=80"
        7  -> "https://images.unsplash.com/photo-1581609939955-6b8ec4d9e5e3?w=800&q=80"
        8  -> "https://images.unsplash.com/photo-1541961017774-22349e4a1262?w=800&q=80"
        9  -> "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800&q=80"
        10 -> "https://images.unsplash.com/photo-1515405295579-ba7b45403062?w=800&q=80"
        else -> "https://images.unsplash.com/photo-1581368135153-a506cf13b1e1?w=800&q=80"
    }

    val artworks: List<Artwork> = listOf(
        // ── Hoysala (5) ──────────────────────────────────────────────────────
        Artwork(
            id = "1", productId = "SKS001",
            title = "Hoysala Bracket Figure — Salabhanjika",
            category = "Hoysala",
            description = "Inspired by the iconic Salabhanjika bracket figures of the Hoysaleswara temple at Halebidu. The sinuous tribhanga posture and intricate jewellery detailing reflect the golden era of Hoysala craftsmanship. Crafted in chloritic schist — the same stone used in the original 12th-century temples.",
            material = "Chloritic Schist", dimensions = "30\" × 12\" × 10\"", year = "2024",
            imageUrl = img(0), price = "₹ 1,20,000", artistName = "Shilpi Ravi Kumar",
            timelineStages = buildTimeline("1", img(0))
        ),
        Artwork(
            id = "2", productId = "SKS002",
            title = "Chennakeshava — Belur Replica",
            category = "Hoysala",
            description = "A faithful recreation of the iconic Chennakeshava deity from Belur temple in Karnataka. Vishnu stands in the samabhanga posture with shankha and chakra, adorned with the intricate star-shaped pedestal characteristic of Hoysala architecture.",
            material = "Soap Stone", dimensions = "36\" × 16\" × 12\"", year = "2024",
            imageUrl = img(1), price = "₹ 2,00,000", artistName = "Shilpi Manjunath",
            timelineStages = buildTimeline("2", img(1))
        ),
        Artwork(
            id = "3", productId = "SKS003",
            title = "Hoysala Panel — Ramayana Frieze",
            category = "Hoysala",
            description = "A horizontal frieze panel replicating the continuous narrative bands found on Hoysala temple walls. Depicts scenes from the Ramayana — from Sita's swayamvara to the Lanka battle — in intricate miniature relief sculpture.",
            material = "Chloritic Schist", dimensions = "48\" × 12\" × 3\"", year = "2023",
            imageUrl = img(2), price = "₹ 3,50,000", artistName = "Shilpi Nagaraj",
            timelineStages = buildTimeline("3", img(2))
        ),
        Artwork(
            id = "4", productId = "SKS004",
            title = "Makara Torana — Temple Gateway",
            category = "Hoysala",
            description = "A miniature Makara Torana — the mythical sea-creature arch typical of Hoysala temple doorways. The piece features intertwined makaras flanking a central deity and is ideal as an ornamental home entrance piece.",
            material = "Black Basalt", dimensions = "36\" × 24\" × 6\"", year = "2024",
            imageUrl = img(3), price = "₹ 4,50,000", artistName = "Shilpi Basavaraju",
            timelineStages = buildTimeline("4", img(3))
        ),
        Artwork(
            id = "5", productId = "SKS005",
            title = "Hoysala Emblem — Sala & the Lion",
            category = "Hoysala",
            description = "The legendary scene of Sala fighting the lion, the founding emblem of the Hoysala dynasty. A masterful circular relief carving capturing the ferocity and courage that defined an empire.",
            material = "Chloritic Schist", dimensions = "24\" × 24\" × 4\"", year = "2024",
            imageUrl = img(4), price = "₹ 1,80,000", artistName = "Shilpi Ravi Kumar",
            timelineStages = buildTimeline("5", img(4))
        ),

        // ── Stone Sculpture (5) ──────────────────────────────────────────────
        Artwork(
            id = "6", productId = "SKS006",
            title = "Nataraja — Lord of Dance",
            category = "Stone Sculpture",
            description = "A masterful rendition of Shiva as Nataraja, carved from black basalt stone. The sculpture captures the cosmic dance representing the cycles of creation and destruction. Each fine detail — from the flowing locks to the ring of fire — is hand-carved with traditional chisels.",
            material = "Black Basalt", dimensions = "24\" × 18\" × 8\"", year = "2024",
            imageUrl = img(5), price = "₹ 85,000", artistName = "Shilpi Venkatesh",
            timelineStages = buildTimeline("6", img(5))
        ),
        Artwork(
            id = "7", productId = "SKS007",
            title = "Ganesha — Seated Vignaharta",
            category = "Stone Sculpture",
            description = "A serene seated Ganesha sculpted with elaborate detail — the modaka sweet in his left hand, the broken tusk, and his vehicle the mouse peeking below the lotus pedestal. The elephant-headed deity of wisdom rendered in warm sandstone.",
            material = "Sandstone", dimensions = "18\" × 12\" × 8\"", year = "2023",
            imageUrl = img(6), price = "₹ 45,000", artistName = "Shilpi Shankar Gowda",
            timelineStages = buildTimeline("7", img(6))
        ),
        Artwork(
            id = "8", productId = "SKS008",
            title = "Nandi Bull — Sacred Mount",
            category = "Stone Sculpture",
            description = "Nandi, the divine vehicle of Shiva, rendered in white granite with exceptional surface finish. The bull sits in a devotional posture facing Shiva's shrine, with traditional flower garland detailing across the neck.",
            material = "White Granite", dimensions = "24\" × 20\" × 16\"", year = "2024",
            imageUrl = img(7), price = "₹ 65,000", artistName = "Shilpi Channappa",
            timelineStages = buildTimeline("8", img(7))
        ),
        Artwork(
            id = "9", productId = "SKS009",
            title = "Lakshmi — Gajalakshmi Panel",
            category = "Stone Sculpture",
            description = "Goddess Lakshmi standing on a lotus, flanked by elephants performing abhisheka. Carved in yellow sandstone with intricate jewellery, this piece embodies the classical feminine ideal with elegant proportions.",
            material = "Yellow Sandstone", dimensions = "24\" × 10\" × 8\"", year = "2024",
            imageUrl = img(8), price = "₹ 75,000", artistName = "Shilpi Manjunath",
            timelineStages = buildTimeline("9", img(8))
        ),
        Artwork(
            id = "10", productId = "SKS010",
            title = "Dancing Apsara — Tribhanga",
            category = "Stone Sculpture",
            description = "A celestial dancer frozen in the tribhanga posture, her waist bent in the classic three-point pose. The Apsara's muslin drape, anklets, and elaborate hairdo are carved with astonishing delicacy in chloritic schist.",
            material = "Chloritic Schist", dimensions = "20\" × 8\" × 6\"", year = "2024",
            imageUrl = img(9), price = "₹ 90,000", artistName = "Shilpi Ravi Kumar",
            timelineStages = buildTimeline("10", img(9))
        ),

        // ── Wood Carving (5) ─────────────────────────────────────────────────
        Artwork(
            id = "11", productId = "SKS011",
            title = "Sandalwood Krishna — Butter Thief",
            category = "Wood Carving",
            description = "Baby Krishna stealing butter from a pot, carved from fragrant Mysore sandalwood. The playful posture and chubby cheeks are rendered with exquisite detail. The piece retains the natural sandalwood fragrance for years.",
            material = "Sandalwood", dimensions = "8\" × 4\" × 4\"", year = "2024",
            imageUrl = img(10), price = "₹ 35,000", artistName = "Shilpi Ganesh Acharya",
            timelineStages = buildTimeline("11", img(10))
        ),
        Artwork(
            id = "12", productId = "SKS012",
            title = "Rosewood Elephant — Ambari Procession",
            category = "Wood Carving",
            description = "A royal elephant carrying a howdah (Ambari) carved from single-block rosewood. Inspired by the Mysore Dasara procession elephants, with intricate gold-painted caparison detailing.",
            material = "Rosewood", dimensions = "12\" × 10\" × 5\"", year = "2023",
            imageUrl = img(11), price = "₹ 28,000", artistName = "Shilpi Suresh Shetty",
            timelineStages = buildTimeline("12", img(11))
        ),
        Artwork(
            id = "13", productId = "SKS013",
            title = "Teak Dwarapalaka — Temple Guardian",
            category = "Wood Carving",
            description = "A pair of Dwarapalaka (temple guardians) carved from aged teak. Standing over two feet tall, these guardians feature elaborate crowns, fierce expressions, and traditional weapons — perfect for flanking a home entrance.",
            material = "Teak Wood", dimensions = "28\" × 8\" × 6\"", year = "2024",
            imageUrl = img(0), price = "₹ 55,000", artistName = "Shilpi Ganesh Acharya",
            timelineStages = buildTimeline("13", img(0))
        ),
        Artwork(
            id = "14", productId = "SKS014",
            title = "Ebony Saraswati — Veena Player",
            category = "Wood Carving",
            description = "Goddess Saraswati seated on a lotus, playing the veena, carved from rare Indian ebony. The dark wood provides a stunning contrast for the fine facial features and flowing garments. A celebration of learning and art.",
            material = "Ebony", dimensions = "14\" × 6\" × 6\"", year = "2024",
            imageUrl = img(1), price = "₹ 48,000", artistName = "Shilpi Suresh Shetty",
            timelineStages = buildTimeline("14", img(1))
        ),
        Artwork(
            id = "15", productId = "SKS015",
            title = "Sandalwood Dashavatara Panel",
            category = "Wood Carving",
            description = "The ten incarnations of Lord Vishnu — from Matsya (fish) to Kalki (future warrior) — arranged in a decorative panel. Each miniature figure is carved with distinct iconography in fragrant Mysore sandalwood.",
            material = "Sandalwood", dimensions = "36\" × 8\" × 2\"", year = "2023",
            imageUrl = img(2), price = "₹ 95,000", artistName = "Shilpi Ganesh Acharya",
            timelineStages = buildTimeline("15", img(2))
        ),

        // ── Bronze Art (5) ───────────────────────────────────────────────────
        Artwork(
            id = "16", productId = "SKS016",
            title = "Chola Bronze — Nataraja",
            category = "Bronze Art",
            description = "A museum-quality bronze Nataraja in the iconic Chola dynasty style. Cast using the lost-wax (cire perdue) method — the same technique used by Tamil artisans for over a thousand years. The flame aureole and flying dreadlocks are cast in a single pour.",
            material = "Bronze (Panchaloha)", dimensions = "18\" × 14\" × 6\"", year = "2024",
            imageUrl = img(3), price = "₹ 1,25,000", artistName = "Sthapathi Radhakrishnan",
            timelineStages = buildTimeline("16", img(3))
        ),
        Artwork(
            id = "17", productId = "SKS017",
            title = "Panchaloha Venkateshwara",
            category = "Bronze Art",
            description = "Lord Venkateshwara (Tirupati Balaji) in the sacred Panchaloha alloy — a blend of gold, silver, copper, zinc, and iron. The deity stands in the stiff samabhanga posture with conch and discus, finished with traditional acid patina.",
            material = "Panchaloha", dimensions = "24\" × 10\" × 8\"", year = "2024",
            imageUrl = img(4), price = "₹ 2,50,000", artistName = "Sthapathi Muthukumar",
            timelineStages = buildTimeline("17", img(4))
        ),
        Artwork(
            id = "18", productId = "SKS018",
            title = "Durga Mahishasuramardini — Bronze",
            category = "Bronze Art",
            description = "The goddess Durga slaying the buffalo demon Mahishasura, a dynamic multi-armed composition. Each of Durga's eight arms holds a divine weapon. Cast in bronze with antique green patina finish using traditional Swamimalai techniques.",
            material = "Bronze", dimensions = "22\" × 16\" × 10\"", year = "2023",
            imageUrl = img(5), price = "₹ 1,80,000", artistName = "Sthapathi Radhakrishnan",
            timelineStages = buildTimeline("18", img(5))
        ),
        Artwork(
            id = "19", productId = "SKS019",
            title = "Ardhanarishvara — Half Man Half Woman",
            category = "Bronze Art",
            description = "The androgynous form of Shiva and Parvati combined, representing the cosmic balance of masculine and feminine energies. The left half shows Parvati's grace while the right shows Shiva's ascetic power. A philosophical masterpiece in bronze.",
            material = "Bronze (Panchaloha)", dimensions = "20\" × 8\" × 6\"", year = "2024",
            imageUrl = img(6), price = "₹ 1,45,000", artistName = "Sthapathi Muthukumar",
            timelineStages = buildTimeline("19", img(6))
        ),
        Artwork(
            id = "20", productId = "SKS020",
            title = "Garuda — Divine Eagle in Bronze",
            category = "Bronze Art",
            description = "The mythical eagle Garuda, vehicle of Lord Vishnu, shown in a semi-kneeling posture with wings partially spread. Cast in Panchaloha bronze using the lost-wax method, with gold leaf highlights on the wing feathers and crown.",
            material = "Panchaloha", dimensions = "16\" × 12\" × 8\"", year = "2024",
            imageUrl = img(7), price = "₹ 95,000", artistName = "Sthapathi Radhakrishnan",
            timelineStages = buildTimeline("20", img(7))
        )
    )

    private fun buildTimeline(artworkId: String, heroImage: String) = listOf(
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 1,
            stageTitle = "Raw Stone Selection",
            stageDescription = "Master artisans travel to ancient quarries in Karnataka to hand-select the finest stone. The rock's grain, texture, and resonance are tested — only stone that 'sings' when tapped is chosen for sculpting.",
            stageImage = "https://images.unsplash.com/photo-1615799998603-7c6270a45196?w=600&q=80",
            durationDays = 3
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 2,
            stageTitle = "Initial Carving",
            stageDescription = "Using iron chisels and wooden mallets, the sculptor removes excess material and establishes the primary form. This stage requires immense strength and a keen three-dimensional vision of the final piece hidden within the raw block.",
            stageImage = "https://images.unsplash.com/photo-1580136579312-94651dfd596d?w=600&q=80",
            durationDays = 14
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 3,
            stageTitle = "Fine Detailing",
            stageDescription = "Delicate tools — some no wider than a pencil — are used to carve jewellery, facial features, and textile folds. This is the most time-intensive stage, demanding years of training and extreme patience from the artisan.",
            stageImage = "https://images.unsplash.com/photo-1555685812-4b943f1cb0eb?w=600&q=80",
            durationDays = 30
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 4,
            stageTitle = "Polishing",
            stageDescription = "The sculpture is progressively polished using increasingly fine grit sandpaper and finally a natural leaf polish. This reveals the stone's inner lustre and the full depth of every carved line and curve.",
            stageImage = "https://images.unsplash.com/photo-1598887141926-29e00d5dc48a?w=600&q=80",
            durationDays = 7
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 5,
            stageTitle = "Final Sculpture",
            stageDescription = "The completed artwork undergoes a final quality review by the master craftsman. A sacred blessing ceremony is performed before the piece leaves the studio — a tradition maintained for over 300 years by Shilpi families.",
            stageImage = heroImage,
            durationDays = 1
        )
    )

    val heritageSections: List<HeritageSection> = listOf(
        HeritageSection(
            id = 1,
            title = "The Hoysala Legacy",
            content = """The Hoysala Empire (10th–14th century CE) produced some of the most extraordinary stone sculpture the world has ever seen. Ruling from their capital at Dwarasamudra (modern Halebidu), the Hoysala kings were prolific temple builders who commissioned thousands of sculptures depicting deities, celestial dancers, mythological narratives, and decorative motifs.

The signature Hoysala style is immediately recognizable: star-shaped temple platforms, densely packed narrative friezes, and figures carved with astonishing miniaturist detail. Unlike the monumental scale of North Indian temple sculpture, Hoysala art rewards close observation — the closer you look, the more you discover.

Today, the UNESCO World Heritage Sites of Belur, Halebidu, and Somanathapura preserve the finest examples of this tradition, drawing scholars and art lovers from across the world.""",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Hoysaleswara_Temple%2C_Halebidu.jpg/1280px-Hoysaleswara_Temple%2C_Halebidu.jpg"
        ),
        HeritageSection(
            id = 2,
            title = "The Art of Stone Carving",
            content = """Karnataka's master stone carvers — called Shilpis — inherit their craft through generations of family apprenticeship. A young Shilpi begins training as young as eight years old, first learning to handle tools safely, then studying the Shilpa Shastras — ancient Sanskrit treatises that codify every aspect of sacred sculpture: proportions, iconography, auspicious postures, and ritual requirements.

The primary stone used in traditional Hoysala sculpture is chloritic schist (locally called Hoysala stone), a metamorphic rock that is soft when quarried but hardens dramatically on exposure to air. This property allows for the extraordinary surface detail seen in Hoysala temples — carvers could work the stone like clay when fresh, and the finished sculpture would achieve the durability of marble.

Modern Shilpis also work in black basalt, sandstone, granite, and soap stone — each material demanding its own set of tools and techniques.""",
            imageUrl = "https://images.unsplash.com/photo-1555685812-4b943f1cb0eb?w=800&q=80"
        ),
        HeritageSection(
            id = 3,
            title = "Cultural & Spiritual Significance",
            content = """In the Hoysala tradition, a sculpture is not merely an art object — it is a living presence. Before work begins, the Shilpi performs a puja (ritual worship) invoking the deity whose form will be carved. Throughout the carving process, the artisan maintains ritual purity, often fasting and praying before beginning each day's work.

When a temple sculpture is completed, it undergoes the Pranapratishtha ceremony — the ritual installation of the divine essence into the stone form. From that moment, the sculpture is no longer stone: it is the deity manifest.

This spiritual relationship between artisan and artwork is what gives Hoysala sculpture its uncanny quality of presence. Even in museum collections far from Karnataka, these sculptures retain a palpable energy — a quality that no amount of mechanical reproduction can replicate.

Shilpa-Kala Showcase exists to connect these living traditions with a global audience, ensuring that the knowledge of master Shilpis reaches the next generation.""",
            imageUrl = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800&q=80"
        )
    )
}
