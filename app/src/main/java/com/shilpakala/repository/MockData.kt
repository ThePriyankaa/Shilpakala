package com.shilpakala.repository

import com.shilpakala.data.model.Artwork
import com.shilpakala.data.model.HeritageSection
import com.shilpakala.data.model.TimelineStage

object MockData {
    // ── Authentic Indian Heritage Sculpture references ─────────────────────────────
    // Carefully curated to match Hoysala, Stone, Wood, and Bronze categories
    
    // Hoysala references
    private val hoysalaImages = listOf(
        "https://commons.wikimedia.org/wiki/Special:FilePath/Darpana_Sundari_(Lady_with_mirror),_Belur.jpg?width=800", // Salabhanjika
        "https://commons.wikimedia.org/wiki/Special:FilePath/Chennakesava_temple_at_Belur.jpg?width=800", // Belur/Halebidu
        "https://commons.wikimedia.org/wiki/Special:FilePath/Friezes_at_Hoysaleswara_Temple.jpg?width=800", // Frieze
        "https://commons.wikimedia.org/wiki/Special:FilePath/Makara_Torana_above_the_doorway_of_Hoysaleshwara_temple.jpg?width=800", // Arch
        "https://commons.wikimedia.org/wiki/Special:FilePath/Hoysala_emblem_at_Belur.jpg?width=800"  // Sala emblem
    )
    
    // Stone Sculpture references
    private val stoneImages = listOf(
        "https://commons.wikimedia.org/wiki/Special:FilePath/Shiva_Nataraja_-_Elephanta.jpg?width=800", // Stone Nataraja
        "https://commons.wikimedia.org/wiki/Special:FilePath/Ganesha_at_Halebidu.jpg?width=800", // Ganesha
        "https://commons.wikimedia.org/wiki/Special:FilePath/Nandi_at_Chamundi_Hills.jpg?width=800", // Nandi
        "https://commons.wikimedia.org/wiki/Special:FilePath/Gajalakshmi_at_Ellora.jpg?width=800", // Gajalakshmi
        "https://commons.wikimedia.org/wiki/Special:FilePath/Apsara_Khajuraho.jpg?width=800"  // Apsara
    )
    
    // Wood Carving references
    private val woodImages = listOf(
        "https://images.unsplash.com/photo-1598887141926-29e00d5dc48a?w=800&q=80", // Keep known good wood carving
        "https://images.unsplash.com/photo-1606103859663-718225529d47?w=800&q=80", 
        "https://commons.wikimedia.org/wiki/Special:FilePath/Wood_carving_at_Kochi.jpg?width=800", // Wood sculpture 
        "https://images.unsplash.com/photo-1610444585324-5e921d7821ee?w=800&q=80", // Sandalwood style
        "https://images.unsplash.com/photo-1629230559648-520ec0cf7f5b?w=800&q=80"  // Elephant wood carving
    )

    // Bronze Art references
    private val bronzeImages = listOf(
        "https://commons.wikimedia.org/wiki/Special:FilePath/Nataraja_Chola.jpg?width=800", // Bronze Nataraja
        "https://commons.wikimedia.org/wiki/Special:FilePath/Bronze_Venkateshwara.jpg?width=800", // Bronze deity
        "https://commons.wikimedia.org/wiki/Special:FilePath/Durga_Mahishasuramardini_bronze.jpg?width=800", // Chola bronze style
        "https://commons.wikimedia.org/wiki/Special:FilePath/Ardhanarishvara_bronze.jpg?width=800", // Panchaloha
        "https://commons.wikimedia.org/wiki/Special:FilePath/Garuda_bronze.jpg?width=800"  // Bronze cast
    )

    val artworks: List<Artwork> = listOf(
        // ── Hoysala (5) ──────────────────────────────────────────────────────
        Artwork(
            id = "1", productId = "SKS001",
            title = "Hoysala Bracket Figure — Salabhanjika",
            category = "Hoysala",
            description = "Inspired by the iconic Salabhanjika bracket figures of the Hoysaleswara temple at Halebidu. The sinuous tribhanga posture and intricate jewellery detailing reflect the golden era of Hoysala craftsmanship. Crafted in chloritic schist — the same stone used in the original 12th-century temples.",
            material = "Chloritic Schist", dimensions = "30\" × 12\" × 10\"", year = "2024",
            imageUrl = hoysalaImages[0], price = "₹ 1,20,000", artistName = "Shilpi Ravi Kumar",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("1", hoysalaImages[0])
        ),
        Artwork(
            id = "2", productId = "SKS002",
            title = "Chennakeshava — Belur Replica",
            category = "Hoysala",
            description = "A faithful recreation of the iconic Chennakeshava deity from Belur temple in Karnataka. Vishnu stands in the samabhanga posture with shankha and chakra, adorned with the intricate star-shaped pedestal characteristic of Hoysala architecture.",
            material = "Soap Stone", dimensions = "36\" × 16\" × 12\"", year = "2024",
            imageUrl = hoysalaImages[1], price = "₹ 2,00,000", artistName = "Shilpi Manjunath",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("2", hoysalaImages[1])
        ),
        Artwork(
            id = "3", productId = "SKS003",
            title = "Hoysala Panel — Ramayana Frieze",
            category = "Hoysala",
            description = "A horizontal frieze panel replicating the continuous narrative bands found on Hoysala temple walls. Depicts scenes from the Ramayana — from Sita's swayamvara to the Lanka battle — in intricate miniature relief sculpture.",
            material = "Chloritic Schist", dimensions = "48\" × 12\" × 3\"", year = "2023",
            imageUrl = hoysalaImages[2], price = "₹ 3,50,000", artistName = "Shilpi Nagaraj",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("3", hoysalaImages[2])
        ),
        Artwork(
            id = "4", productId = "SKS004",
            title = "Makara Torana — Temple Gateway",
            category = "Hoysala",
            description = "A miniature Makara Torana — the mythical sea-creature arch typical of Hoysala temple doorways. The piece features intertwined makaras flanking a central deity and is ideal as an ornamental home entrance piece.",
            material = "Black Basalt", dimensions = "36\" × 24\" × 6\"", year = "2024",
            imageUrl = hoysalaImages[3], price = "₹ 4,50,000", artistName = "Shilpi Basavaraju",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("4", hoysalaImages[3])
        ),
        Artwork(
            id = "5", productId = "SKS005",
            title = "Hoysala Emblem — Sala & the Lion",
            category = "Hoysala",
            description = "The legendary scene of Sala fighting the lion, the founding emblem of the Hoysala dynasty. A masterful circular relief carving capturing the ferocity and courage that defined an empire.",
            material = "Chloritic Schist", dimensions = "24\" × 24\" × 4\"", year = "2024",
            imageUrl = hoysalaImages[4], price = "₹ 1,80,000", artistName = "Shilpi Ravi Kumar",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("5", hoysalaImages[4])
        ),

        // ── Stone Sculpture (5) ──────────────────────────────────────────────
        Artwork(
            id = "6", productId = "SKS006",
            title = "Nataraja — Lord of Dance",
            category = "Stone Sculpture",
            description = "A masterful rendition of Shiva as Nataraja, carved from black basalt stone. The sculpture captures the cosmic dance representing the cycles of creation and destruction. Each fine detail — from the flowing locks to the ring of fire — is hand-carved with traditional chisels.",
            material = "Black Basalt", dimensions = "24\" × 18\" × 8\"", year = "2024",
            imageUrl = stoneImages[0], price = "₹ 85,000", artistName = "Shilpi Venkatesh",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("6", stoneImages[0])
        ),
        Artwork(
            id = "7", productId = "SKS007",
            title = "Ganesha — Seated Vignaharta",
            category = "Stone Sculpture",
            description = "A serene seated Ganesha sculpted with elaborate detail — the modaka sweet in his left hand, the broken tusk, and his vehicle the mouse peeking below the lotus pedestal. The elephant-headed deity of wisdom rendered in warm sandstone.",
            material = "Sandstone", dimensions = "18\" × 12\" × 8\"", year = "2023",
            imageUrl = stoneImages[1], price = "₹ 45,000", artistName = "Shilpi Shankar Gowda",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("7", stoneImages[1])
        ),
        Artwork(
            id = "8", productId = "SKS008",
            title = "Nandi Bull — Sacred Mount",
            category = "Stone Sculpture",
            description = "Nandi, the divine vehicle of Shiva, rendered in white granite with exceptional surface finish. The bull sits in a devotional posture facing Shiva's shrine, with traditional flower garland detailing across the neck.",
            material = "White Granite", dimensions = "24\" × 20\" × 16\"", year = "2024",
            imageUrl = stoneImages[2], price = "₹ 65,000", artistName = "Shilpi Channappa",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("8", stoneImages[2])
        ),
        Artwork(
            id = "9", productId = "SKS009",
            title = "Lakshmi — Gajalakshmi Panel",
            category = "Stone Sculpture",
            description = "Goddess Lakshmi standing on a lotus, flanked by elephants performing abhisheka. Carved in yellow sandstone with intricate jewellery, this piece embodies the classical feminine ideal with elegant proportions.",
            material = "Yellow Sandstone", dimensions = "24\" × 10\" × 8\"", year = "2024",
            imageUrl = stoneImages[3], price = "₹ 75,000", artistName = "Shilpi Manjunath",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("9", stoneImages[3])
        ),
        Artwork(
            id = "10", productId = "SKS010",
            title = "Dancing Apsara — Tribhanga",
            category = "Stone Sculpture",
            description = "A celestial dancer frozen in the tribhanga posture, her waist bent in the classic three-point pose. The Apsara's muslin drape, anklets, and elaborate hairdo are carved with astonishing delicacy in chloritic schist.",
            material = "Chloritic Schist", dimensions = "20\" × 8\" × 6\"", year = "2024",
            imageUrl = stoneImages[4], price = "₹ 90,000", artistName = "Shilpi Ravi Kumar",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("10", stoneImages[4])
        ),

        // ── Wood Carving (5) ─────────────────────────────────────────────────
        Artwork(
            id = "11", productId = "SKS011",
            title = "Sandalwood Krishna — Butter Thief",
            category = "Wood Carving",
            description = "Baby Krishna stealing butter from a pot, carved from fragrant Mysore sandalwood. The playful posture and chubby cheeks are rendered with exquisite detail. The piece retains the natural sandalwood fragrance for years.",
            material = "Sandalwood", dimensions = "8\" × 4\" × 4\"", year = "2024",
            imageUrl = woodImages[0], price = "₹ 35,000", artistName = "Shilpi Ganesh Acharya",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("11", woodImages[0])
        ),
        Artwork(
            id = "12", productId = "SKS012",
            title = "Rosewood Elephant — Ambari Procession",
            category = "Wood Carving",
            description = "A royal elephant carrying a howdah (Ambari) carved from single-block rosewood. Inspired by the Mysore Dasara procession elephants, with intricate gold-painted caparison detailing.",
            material = "Rosewood", dimensions = "12\" × 10\" × 5\"", year = "2023",
            imageUrl = woodImages[1], price = "₹ 28,000", artistName = "Shilpi Suresh Shetty",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("12", woodImages[1])
        ),
        Artwork(
            id = "13", productId = "SKS013",
            title = "Teak Dwarapalaka — Temple Guardian",
            category = "Wood Carving",
            description = "A pair of Dwarapalaka (temple guardians) carved from aged teak. Standing over two feet tall, these guardians feature elaborate crowns, fierce expressions, and traditional weapons — perfect for flanking a home entrance.",
            material = "Teak Wood", dimensions = "28\" × 8\" × 6\"", year = "2024",
            imageUrl = woodImages[2], price = "₹ 55,000", artistName = "Shilpi Ganesh Acharya",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("13", woodImages[2])
        ),
        Artwork(
            id = "14", productId = "SKS014",
            title = "Ebony Saraswati — Veena Player",
            category = "Wood Carving",
            description = "Goddess Saraswati seated on a lotus, playing the veena, carved from rare Indian ebony. The dark wood provides a stunning contrast for the fine facial features and flowing garments. A celebration of learning and art.",
            material = "Ebony", dimensions = "14\" × 6\" × 6\"", year = "2024",
            imageUrl = woodImages[3], price = "₹ 48,000", artistName = "Shilpi Suresh Shetty",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("14", woodImages[3])
        ),
        Artwork(
            id = "15", productId = "SKS015",
            title = "Sandalwood Dashavatara Panel",
            category = "Wood Carving",
            description = "The ten incarnations of Lord Vishnu — from Matsya (fish) to Kalki (future warrior) — arranged in a decorative panel. Each miniature figure is carved with distinct iconography in fragrant Mysore sandalwood.",
            material = "Sandalwood", dimensions = "36\" × 8\" × 2\"", year = "2023",
            imageUrl = woodImages[4], price = "₹ 95,000", artistName = "Shilpi Ganesh Acharya",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("15", woodImages[4])
        ),

        // ── Bronze Art (5) ───────────────────────────────────────────────────
        Artwork(
            id = "16", productId = "SKS016",
            title = "Chola Bronze — Nataraja",
            category = "Bronze Art",
            description = "A museum-quality bronze Nataraja in the iconic Chola dynasty style. Cast using the lost-wax (cire perdue) method — the same technique used by Tamil artisans for over a thousand years. The flame aureole and flying dreadlocks are cast in a single pour.",
            material = "Bronze (Panchaloha)", dimensions = "18\" × 14\" × 6\"", year = "2024",
            imageUrl = bronzeImages[0], price = "₹ 1,25,000", artistName = "Sthapathi Radhakrishnan",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("16", bronzeImages[0])
        ),
        Artwork(
            id = "17", productId = "SKS017",
            title = "Panchaloha Venkateshwara",
            category = "Bronze Art",
            description = "Lord Venkateshwara (Tirupati Balaji) in the sacred Panchaloha alloy — a blend of gold, silver, copper, zinc, and iron. The deity stands in the stiff samabhanga posture with conch and discus, finished with traditional acid patina.",
            material = "Panchaloha", dimensions = "24\" × 10\" × 8\"", year = "2024",
            imageUrl = bronzeImages[1], price = "₹ 2,50,000", artistName = "Sthapathi Muthukumar",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("17", bronzeImages[1])
        ),
        Artwork(
            id = "18", productId = "SKS018",
            title = "Durga Mahishasuramardini — Bronze",
            category = "Bronze Art",
            description = "The goddess Durga slaying the buffalo demon Mahishasura, a dynamic multi-armed composition. Each of Durga's eight arms holds a divine weapon. Cast in bronze with antique green patina finish using traditional Swamimalai techniques.",
            material = "Bronze", dimensions = "22\" × 16\" × 10\"", year = "2023",
            imageUrl = bronzeImages[2], price = "₹ 1,80,000", artistName = "Sthapathi Radhakrishnan",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("18", bronzeImages[2])
        ),
        Artwork(
            id = "19", productId = "SKS019",
            title = "Ardhanarishvara — Half Man Half Woman",
            category = "Bronze Art",
            description = "The androgynous form of Shiva and Parvati combined, representing the cosmic balance of masculine and feminine energies. The left half shows Parvati's grace while the right shows Shiva's ascetic power. A philosophical masterpiece in bronze.",
            material = "Bronze (Panchaloha)", dimensions = "20\" × 8\" × 6\"", year = "2024",
            imageUrl = bronzeImages[3], price = "₹ 1,45,000", artistName = "Sthapathi Muthukumar",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("19", bronzeImages[3])
        ),
        Artwork(
            id = "20", productId = "SKS020",
            title = "Garuda — Divine Eagle in Bronze",
            category = "Bronze Art",
            description = "The mythical eagle Garuda, vehicle of Lord Vishnu, shown in a semi-kneeling posture with wings partially spread. Cast in Panchaloha bronze using the lost-wax method, with gold leaf highlights on the wing feathers and crown.",
            material = "Panchaloha", dimensions = "16\" × 12\" × 8\"", year = "2024",
            imageUrl = bronzeImages[4], price = "₹ 95,000", artistName = "Sthapathi Radhakrishnan",
            whatsappNumber = "+919876543210",
            timelineStages = buildTimeline("20", bronzeImages[4])
        )
    )

    private fun buildTimeline(artworkId: String, heroImage: String) = listOf(
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 1,
            stageTitle = "Raw Material Selection",
            stageDescription = "Master artisans travel to source the finest materials. The material's quality and resonance are tested before being chosen for sculpting.",
            stageImage = "https://images.unsplash.com/photo-1615799998603-7c6270a45196?w=600&q=80",
            durationDays = 3
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 2,
            stageTitle = "Initial Carving",
            stageDescription = "Using traditional tools, the sculptor removes excess material and establishes the primary form. This stage requires immense strength and a keen three-dimensional vision.",
            stageImage = "https://images.unsplash.com/photo-1580136579312-94651dfd596d?w=600&q=80",
            durationDays = 14
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 3,
            stageTitle = "Fine Detailing",
            stageDescription = "Delicate tools are used to carve jewellery, facial features, and folds. This is the most time-intensive stage, demanding years of training and patience.",
            stageImage = "https://images.unsplash.com/photo-1555685812-4b943f1cb0eb?w=600&q=80",
            durationDays = 30
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 4,
            stageTitle = "Polishing & Finishing",
            stageDescription = "The sculpture is progressively polished to reveal the material's inner lustre and the full depth of every carved line.",
            stageImage = "https://images.unsplash.com/photo-1598887141926-29e00d5dc48a?w=600&q=80",
            durationDays = 7
        ),
        TimelineStage(
            id = 0, artworkId = artworkId, stageOrder = 5,
            stageTitle = "Final Masterpiece",
            stageDescription = "The completed artwork undergoes a final quality review by the master craftsman.",
            stageImage = heroImage,
            durationDays = 1
        )
    )

    val heritageSections: List<HeritageSection> = listOf(
        HeritageSection(
            id = "1",
            category = "Hoysala Art",
            title = "The Hoysala Legacy",
            content = "The Hoysala Empire (10th–14th century CE) produced some of the most extraordinary stone sculpture the world has ever seen. Ruling from their capital at Dwarasamudra (modern Halebidu), the Hoysala kings were prolific temple builders who commissioned thousands of sculptures depicting deities, celestial dancers, mythological narratives, and decorative motifs.\n\nThe signature Hoysala style is immediately recognizable: star-shaped temple platforms, densely packed narrative friezes, and figures carved with astonishing miniaturist detail. Unlike the monumental scale of North Indian temple sculpture, Hoysala art rewards close observation — the closer you look, the more you discover.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Hoysaleswara_Temple%2C_Halebidu.jpg/1280px-Hoysaleswara_Temple%2C_Halebidu.jpg",
            region = "Karnataka",
            historicalPeriod = "10th–14th Century CE"
        ),
        HeritageSection(
            id = "2",
            category = "Stone Carving",
            title = "The Art of Stone Carving",
            content = "Karnataka's master stone carvers — called Shilpis — inherit their craft through generations of family apprenticeship. A young Shilpi begins training as young as eight years old, first learning to handle tools safely, then studying the Shilpa Shastras — ancient Sanskrit treatises that codify every aspect of sacred sculpture: proportions, iconography, auspicious postures, and ritual requirements.\n\nThe primary stone used in traditional Hoysala sculpture is chloritic schist (locally called Hoysala stone), a metamorphic rock that is soft when quarried but hardens dramatically on exposure to air. Modern Shilpis also work in black basalt, sandstone, granite, and soap stone — each material demanding its own set of tools and techniques.",
            imageUrl = "https://images.unsplash.com/photo-1555685812-4b943f1cb0eb?w=800&q=80",
            region = "South India",
            historicalPeriod = "Ancient to Modern"
        ),
        HeritageSection(
            id = "3",
            category = "Wood Carving",
            title = "Sandalwood & Rosewood Mastery",
            content = "Wood carving in Karnataka has a rich history deeply rooted in its dense forests, particularly the sandalwood regions of Mysore and the rosewood forests. The artisans, often from the Vishwakarma community, employ ancient techniques passed down through generations.\n\nThe intricate details achieved on Mysore sandalwood, famous for its distinct fragrance, are unmatched. Themes often revolve around Hindu mythology, royal processions like the famous Mysore Dasara Ambari, and intricate floral patterns. Rosewood carving features inlay work (marquetry) with materials like ivory (now replaced by synthetic alternatives) or lighter woods, creating a stunning contrast.",
            imageUrl = "https://images.unsplash.com/photo-1598887141926-29e00d5dc48a?w=800&q=80",
            region = "Mysore Region",
            historicalPeriod = "18th Century Onwards"
        ),
        HeritageSection(
            id = "4",
            category = "Bronze Sculptures",
            title = "Panchaloha Castings",
            content = "While the Chola dynasty of Tamil Nadu is most famous for bronze casting, the art form flourished across South India. The traditional 'lost-wax' (cire perdue) technique is used to create exquisite idols of deities, dancers, and historical figures.\n\nThe use of 'Panchaloha' — a sacred alloy of five metals (gold, silver, copper, zinc, and iron) — is central to this craft. The metal is believed to possess spiritual properties that enhance the divine presence when installed in a temple or home.",
            imageUrl = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800&q=80",
            region = "Deccan & Tamil Nadu",
            historicalPeriod = "9th Century Onwards"
        ),
        HeritageSection(
            id = "5",
            category = "Temple Architecture",
            title = "Dravidian to Vesara Styles",
            content = "The temple architecture of South India is a testament to the region's engineering brilliance and spiritual devotion. Karnataka specifically represents the transition from the Dravidian style of the south to the Nagara style of the north, giving birth to the unique Vesara style.\n\nThe Chalukyas of Badami experimented with rock-cut architecture, while the Hoysalas perfected the star-shaped ground plans and intricately carved pillars. Every element of the temple, from the towering Vimana to the detailed Adhishthana (base), was designed to evoke a sense of awe and reverence.",
            imageUrl = "https://images.unsplash.com/photo-1581609939955-6b8ec4d9e5e3?w=800&q=80",
            region = "South India",
            historicalPeriod = "6th–16th Century CE"
        )
    )
}
