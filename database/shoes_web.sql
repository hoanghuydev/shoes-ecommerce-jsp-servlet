-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: shoes_web
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `fullName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `phoneNumber` int DEFAULT NULL,
                           `province` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `district` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `commune` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `hamlet` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `createAt` timestamp NULL DEFAULT (now()),
                           `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `userId` bigint DEFAULT NULL,
                        `orderDetailsId` bigint DEFAULT NULL,
                        `createAt` timestamp NULL DEFAULT (now()),
                        `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                        PRIMARY KEY (`id`),
                        KEY `Cart_fk0` (`userId`),
                        KEY `Cart_fk1` (`orderDetailsId`),
                        CONSTRAINT `Cart_fk0` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
                        CONSTRAINT `Cart_fk1` FOREIGN KEY (`orderDetailsId`) REFERENCES `orderdetails` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                            `code` varchar(255) DEFAULT NULL,
                            `createAt` timestamp NULL DEFAULT (now()),
                            `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Lifestyle','lifestyle','2024-01-01 20:24:34','2024-01-01 20:24:34',false),(2,'Basketball','basketball','2024-01-01 20:24:34','2024-01-01 20:24:34',false),(3,'Football','football','2024-01-01 20:24:34','2024-01-01 20:24:34',false),(4,'Baseball','baseball','2024-01-01 20:24:34','2024-01-01 20:24:34',false),(5,'Volleyball','volleyball','2024-01-01 20:24:34','2024-01-01 20:24:34',false),(6,'Golf','golf','2024-01-01 20:24:34','2024-01-01 20:24:34',false),(7,'Running','running','2024-01-01 20:24:34','2024-01-01 20:24:34',false);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `amount` int DEFAULT '0',
                             `productSizeId` bigint DEFAULT NULL,
                             `createAt` timestamp NULL DEFAULT (now()),
                             `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                             PRIMARY KEY (`id`),
                             KEY `Inventory_productsize_id_fk` (`productSizeId`),
                             CONSTRAINT `Inventory_productsize_id_fk` FOREIGN KEY (`productSizeId`) REFERENCES `productsize` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notify`
--

DROP TABLE IF EXISTS `notify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notify` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
                          `isSeen` tinyint(1) DEFAULT '0',
                          `createAt` timestamp NULL DEFAULT (now()),
                          `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notify`
--

LOCK TABLES `notify` WRITE;
/*!40000 ALTER TABLE `notify` DISABLE KEYS */;
/*!40000 ALTER TABLE `notify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opinion`
--

DROP TABLE IF EXISTS `opinion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opinion` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `title` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `rating` int DEFAULT NULL,
                           `productId` bigint DEFAULT NULL,
                           `userId` bigint DEFAULT NULL,
                           `createAt` timestamp NULL DEFAULT (now()),
                           `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                           PRIMARY KEY (`id`),
                           KEY `comment_product_id_fk` (`productId`),
                           KEY `opinion_user_id_fk` (`userId`),
                           CONSTRAINT `comment_product_id_fk` FOREIGN KEY (`productId`) REFERENCES `product` (`id`),
                           CONSTRAINT `opinion_user_id_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opinion`
--

LOCK TABLES `opinion` WRITE;
/*!40000 ALTER TABLE `opinion` DISABLE KEYS */;
/*!40000 ALTER TABLE `opinion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `status` int DEFAULT '0',
                         `note` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                         `totalAmount` decimal(10,1) DEFAULT NULL,
                         `addressId` bigint DEFAULT NULL,
                         `createAt` timestamp NULL DEFAULT (now()),
                         `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                         PRIMARY KEY (`id`),
                         KEY `Order_fk0` (`addressId`),
                         CONSTRAINT `Order_fk0` FOREIGN KEY (`addressId`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetails` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `quantity` int DEFAULT NULL,
                                `subTotal` decimal(10,1) DEFAULT NULL,
                                `orderId` bigint DEFAULT NULL,
                                `productSizeId` bigint DEFAULT NULL,
                                `createAt` timestamp NULL DEFAULT (now()),
                                `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                                PRIMARY KEY (`id`),
                                KEY `OrderDetails_fk0` (`orderId`),
                                KEY `OrderDetails_fk1` (`productSizeId`),
                                CONSTRAINT `OrderDetails_fk0` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`),
                                CONSTRAINT `OrderDetails_fk1` FOREIGN KEY (`productSizeId`) REFERENCES `productsize` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetails`
--

LOCK TABLES `orderdetails` WRITE;
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name` varchar(350) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                           `thumbnail` varchar(3000) NOT NULL,
                           `shortDescription` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `price` decimal(10,1) DEFAULT NULL,
                           `modelUrl` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `slug` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `categoryId` bigint DEFAULT NULL,
                           `createAt` timestamp NULL DEFAULT (now()),
                           `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                           PRIMARY KEY (`id`),
                           KEY `product_category_id_fk` (`categoryId`),
                           CONSTRAINT `product_category_id_fk` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Nike Air Max 90','&lt;p&gt;You tryna be comfortable all day, every day? We got you. The AJ1 Low has all the looks of the hoops original, with less weight and a sleek profile. Plus, they go with any \'fit&mdash;just lace up and go.&lt;/p&gt;&lt;p&gt;&lt;br&gt;&amp;nbsp;&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Nike Air technology absorbs impact for cushioning with every step.&lt;/li&gt;&lt;li&gt;Genuine and synthetic leather and textile materials combine for lightweight durability and a great fit.&lt;/li&gt;&lt;li&gt;Rubber outsole provides ample traction.&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;br&gt;&amp;nbsp;&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Jumpman logo on tongue&lt;/li&gt;&lt;li&gt;Stitched-down Swoosh logo&lt;ul&gt;&lt;li&gt;Shown: Black/Black/White&lt;/li&gt;&lt;li&gt;Style: DV0990-001&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704212059/zdyy9vcookwn8pe9cgh6.png','Inspired by the original that debuted in 1985, the Air Jordan 1 Low offers a clean, classic look that\'s familiar yet always fresh. With an iconic design that pairs perfectly with any \'fit, these kicks ensure you\'ll always be on point.',100.0,NULL,NULL,1,'2024-01-02 16:14:19','2024-01-02 16:14:19',false),(2,'Nike Tech Hera','&lt;p&gt;Rooted in early 2000s running, the Tech Hera is here to fulfill all of your chunky sneaker wishes. The wavy lifted midsole and aged neutral palette level up your look while keeping you comfortable. Its durable design holds up beautifully to everyday wear&mdash;which is perfect, because you will definitely want to wear these every day.&lt;/p&gt;&lt;p&gt;&lt;br&gt;&amp;nbsp;&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Upper combines airy mesh with real and synthetic leather for dimension and durability.&lt;/li&gt;&lt;li&gt;Chunky design has a subtle platform to give you a little extra height.&lt;/li&gt;&lt;li&gt;Full-length rubber outsole delivers durable traction.&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;br&gt;&amp;nbsp;&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Padded collar&lt;/li&gt;&lt;li&gt;Pull tab on heel&lt;/li&gt;&lt;li&gt;Embroidered Swoosh logo&lt;ul&gt;&lt;li&gt;Shown: Sail/Light Orewood Brown/Coconut Milk/Campfire Orange&lt;/li&gt;&lt;li&gt;Style: FQ8107-133&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704215356/nw9wcfdali4xqcvysmlm.png','Rooted in early 2000s running, the Tech Hera is here to fulfill all of your chunky sneaker wishes. The wavy lifted midsole and aged neutral palette level up your look while keeping you comfortable.',110.0,NULL,NULL,1,'2024-01-02 17:09:16','2024-01-02 17:09:16',false),(3,'Nike Mercurial Superfly 9 Club','&lt;p&gt;Instantly tilt the pitch in the bold design of the light and low-to-the-ground Superfly 9 Club MG. Fast is in the Air.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Drive your speed&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The speed cage inside the structure is made from a thin but strong material that secures the foot to the outsole without adding additional weight for optimal lockdown.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Dig in, take off&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Unique traction pattern offers super-charged traction with quick release to create separation.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Improved fit&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Flyknit wraps your ankle in soft, stretchy fabric for a secure feel. A redone design improves the fit, so that it better simulates the foot. We did this by conducting multiple wear tests on hundreds of athletes. The result is a more contoured toe box and better lockdown in the heel.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Feel the ball&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;A moulded, synthetic upper has a textured pattern for better ball control when dribbling at high speeds.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Product details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;For use on natural and synthetic surfaces&lt;/li&gt;&lt;li&gt;Cushioned insole&lt;/li&gt;&lt;li&gt;Colour Shown: Black/Hyper Royal/Chrome&lt;/li&gt;&lt;li&gt;Style: DJ5961-040&lt;/li&gt;&lt;li&gt;Country/Region of Origin: Vietnam&lt;/li&gt;&lt;/ul&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704233310/eqmlo8nlmbifxinsegpf.png','Instantly tilt the pitch in the bold design of the light and low-to-the-ground Superfly 9 Club MG. Fast is in the Air.',80.9,NULL,NULL,7,'2024-01-02 22:08:47','2024-01-02 22:08:47',false),(4,'Nike Go FlyEase','&lt;p&gt;Ditch the laces and get outside. These kicks feature Nike\'s revolutionary FlyEase technology, making on-and-off a breeze. With a heel that pivots open for a totally hands-free entry, they\'re great for people with limited mobility&mdash;or anyone who wants a quicker way to get going.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Step In and Go&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The entire heel (including the sole) hinges open and stays open until you\'re ready. Just slip in and step down to make the heel move back into place and secure your foot.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;&quot;CushIon&quot; Every Step&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Plush Cushlon foam gives each heel-to-toe transition a smooth, cushioned feeling.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Lightweight Structure&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Airy fabric in the upper lets your feet breathe while durable, no-sew overlays provide structure and stability.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Grippy rubber outsole&lt;/li&gt;&lt;li&gt;Swoosh design&lt;/li&gt;&lt;li&gt;Colour Shown: Sea Glass/Guava Ice/Luminous Green/Light Silver&lt;/li&gt;&lt;li&gt;Style: DR5540-005&lt;/li&gt;&lt;li&gt;Country/Region of Origin: Vietnam&lt;/li&gt;&lt;/ul&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704233684/ahtnmfvovwgx38bgt2d6.png','Ditch the laces and get outside. These kicks feature Nike\'s revolutionary FlyEase technology, making on-and-off a breeze',155.8,NULL,NULL,6,'2024-01-02 22:14:43','2024-01-02 22:14:43',false),(5,'Nike Air Huarache Runner','&lt;p&gt;When it fits this well and looks this good, it doesn\'t need a Swoosh logo. The Air Huarache takes on a classic silhouette with a runner-inspired design, mixed materials and rich neutrals for a look that is both nostalgic and brand new. With its stretchy, foot-hugging fabric and futuristic heel cage, it\'s still everything you love about the Huarache.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Inspired by water skiing, the stretchy upper and cushioned inner sleeve create a snug fit for an incredible feel.&lt;/li&gt;&lt;li&gt;Originally designed for performance running, Nike Air cushioning delivers lasting comfort.&lt;/li&gt;&lt;li&gt;A caged heel adds support and is paired with an elongated tongue to keep the early \'90s look you love.&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Product details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Rubber sole&lt;/li&gt;&lt;li&gt;Variable-width lacing&lt;/li&gt;&lt;li&gt;Not intended for use as Personal Protective Equipment (PPE)&lt;/li&gt;&lt;li&gt;Colour Shown: Dark Obsidian/Obsidian/Gum Dark Brown/White&lt;/li&gt;&lt;li&gt;Style: DZ3306-400&lt;/li&gt;&lt;li&gt;Country/Region of Origin: China&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Huarache Origins&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The Nike Air Huarache released in 1991. Footwear designer Tinker Hatfield wanted to combine the fit of neoprene water-ski boots with the functionality of South American sandals. The instant hit found its way to basketball courts and then the streets, where it earned its status as an icon.&lt;/p&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704234050/gdrknjwyagdjpxig1yd6.png','When it fits this well and looks this good, it doesn\'t need a Swoosh logo.',200.0,NULL,NULL,7,'2024-01-02 22:20:50','2024-01-02 22:20:50',false),(7,'Nike Waffle Debut','&lt;p&gt;Free Shipping*&lt;/p&gt;&lt;p&gt;To get accurate shipping information &lt;a href=&quot;/user/address&quot;&gt;Edit Location&lt;/a&gt;&lt;/p&gt;&lt;p&gt;&lt;br&gt;Retro gets modernized with these sleek sneaks inspired by the Nike Daybreak. Era-echoing suede and nylon are paired in complementary colors, and the updated wedge midsole gives you an extra lift. Style, comfort, iconic Waffle outsole&mdash;this is a perfect new addition to your daily rotation.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Swoosh wraps around your heel to form a pull tab, adding aesthetic appeal to on-and-off functionality.&lt;/li&gt;&lt;li&gt;Lifted foam midsoles give you an elevated look and all-day comfort.&lt;/li&gt;&lt;li&gt;Soft suede overlays nod to vintage materials while textile underlays add durability.&lt;/li&gt;&lt;li&gt;Rubber Waffle outsole adds durable traction and heritage style.&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Padded collar&lt;/li&gt;&lt;li&gt;Exposed foam tongue&lt;/li&gt;&lt;li&gt;Traditional lacing&lt;/li&gt;&lt;li&gt;Perforations on heel&lt;ul&gt;&lt;li&gt;Shown: White/Light Orewood Brown/Sail/Rush Fuchsia&lt;/li&gt;&lt;li&gt;Style: DH9523-104&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Waffle Origin&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;In 1971, Nike cofounder Bill Bowerman was watching his wife, Barbra, make breakfast on a waffle iron and inspiration struck. He swapped batter for rubber and the Waffle outsole was born. It not only delivers traction, durability and heritage Nike style&mdash;it proves that greatness is in the everyday. And that old, rusty waffle iron? Well, it now sits on a pedestal at Nike World Headquarters.&lt;/p&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704268740/bkrq1pgyze889v5be2gp.png','Retro gets modernized with these sleek sneaks inspired by the Nike Daybreak.',64.0,NULL,NULL,2,'2024-01-03 07:58:59','2024-01-03 07:58:59',false),(8,'Nike Elevate 3','&lt;p&gt;&lt;strong&gt;Free Shipping*&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;To get accurate shipping information &lt;a href=&quot;/user/address&quot;&gt;Edit Location&lt;/a&gt;&lt;/p&gt;&lt;p&gt;Level up your game on both ends of the floor in the Nike Renew Elevate 3. Specifically tuned for 2-way players who want to make an impact offensively and defensively, this shoe helps you optimize your ability with all-game, all-season support and stability. Improved traction and arch support enhance cutting and pivoting, which can make the difference down the stretch.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;On-Point Pivots&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;A generative, athlete-tested traction allows for better pivoting around the forefoot, allowing you to feel more secure and locked in when spinning, stopping and starting.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Lace Up&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;We added extra lace holes on the medial midfoot to give you the extra security you need for all-important arch support.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;More Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Structured mesh in the upper feels plush around your foot. It fits snugly to help reduce in-shoe movement during play.&lt;/li&gt;&lt;li&gt;The padded collar is contoured to provide a precise fit and support around your ankle.&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;No-sew overlays&lt;/li&gt;&lt;li&gt;Foam midsole&lt;/li&gt;&lt;li&gt;Plush tongue&lt;ul&gt;&lt;li&gt;Shown: Black/Wolf Grey/Cool Grey/White&lt;/li&gt;&lt;li&gt;Style: DD9304-002&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&amp;nbsp;&lt;/p&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704269225/qheu2fgefuytcnnzpmgg.png','Level up your game on both ends of the floor in the Nike Renew Elevate 3',85.0,NULL,NULL,2,'2024-01-03 08:07:05','2024-01-03 08:07:05',false),(9,'Nike Air Max SC','&lt;p&gt;&lt;strong&gt;Free Shipping*&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;To get accurate shipping information &lt;a href=&quot;/user/address&quot;&gt;Edit Location&lt;/a&gt;&lt;/p&gt;&lt;p&gt;With its familiar design lines, heritage track aesthetic and visible Max Air cushioning, the Nike Air Max SC is the perfect way to finish off any outfit. Smooth leather and lightweight knit textiles add depth and durability while a tinted Air unit in the heel brightens your day with every step.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Leather combines with airy knits and mesh for durable construction that\'s breathable and comfortable enough to wear all day.&lt;/li&gt;&lt;li&gt;Originally designed for running, Max Air unit in heels deliver lightweight cushioning with every step.&lt;/li&gt;&lt;li&gt;Foam midsole adds cushioning that lasts.&lt;/li&gt;&lt;li&gt;Rubber outsole provides traction and durability.&lt;ul&gt;&lt;li&gt;Shown: White/Light Lemon Twist/Fireberry/Blue Tint&lt;/li&gt;&lt;li&gt;Style: FQ8886-100&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Nike Air Max Origins&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Revolutionary Air technology first made its way into Nike footwear in 1978. In 1987, the Air Max 1 debuted with visible Air technology in its heel, allowing fans more than just the feel of Air cushioning&mdash;suddenly they could see it. Since then, next-generation Air Max shoes have become a hit with athletes and collectors by offering striking color combinations and reliable, lightweight cushioning.&lt;/p&gt;&lt;p&gt;&amp;nbsp;&lt;/p&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704269655/kut4qnvut5cpwloxgrqy.png','With its familiar design lines, heritage track aesthetic and visible Max Air cushioning, the Nike Air Max SC is the perfect way to finish off any outfit',68.0,NULL,NULL,5,'2024-01-03 08:14:14','2024-01-03 08:14:14',false),(10,'Nike Air Max 270','&lt;p&gt;Free Shipping*&lt;/p&gt;&lt;p&gt;To get accurate shipping information &lt;a href=&quot;/user/address&quot;&gt;Edit Location&lt;/a&gt;&lt;/p&gt;&lt;p&gt;&lt;strong&gt;LEGENDARY AIR GETS LIFTED.&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Nike\'s first lifestyle Air Max brings you style, comfort and big Air in the Nike Air Max 270. The design draws inspiration from Air Max icons, showcasing Nike\'s huge innovation with its large window and fresh details.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;The Max Air 270 unit delivers all-day comfort.&lt;/li&gt;&lt;li&gt;Knit and synthetic fabric on the upper provides a lightweight fit and airy feel.&lt;/li&gt;&lt;li&gt;Foam midsole feels soft and comfortable.&lt;/li&gt;&lt;li&gt;Stretchy inner sleeve creates a personalized fit.&lt;/li&gt;&lt;li&gt;Rubber on the outsole adds traction and durability.&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Mesh details for an airy feel&lt;/li&gt;&lt;li&gt;Synthetic and textile&lt;/li&gt;&lt;li&gt;2-piece midsole&lt;ul&gt;&lt;li&gt;Shown: Summit White/Desert Sand/Peach Cream/Black&lt;/li&gt;&lt;li&gt;Style: DH3050-100&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Nike Air Max Origins&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Revolutionary Air technology first made its way into Nike footwear in 1978. In 1987, the Air Max 1 debuted with visible Air technology in its heel, allowing fans more than just the feel of Air cushioning&mdash;suddenly they could see it. Since then, next-generation Air Max shoes have become a hit with athletes and collectors by offering striking color combinations and reliable, lightweight cushioning.&lt;/p&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704269813/ftllqqdrl0sveehjomp9.png','Nike\'s first lifestyle Air Max brings you style, comfort and big Air in the Nike Air Max 270.',160.0,NULL,NULL,3,'2024-01-03 08:16:52','2024-01-03 08:16:52',false),(11,'Nike Air Max 90','&lt;p&gt;Free Shipping*&lt;/p&gt;&lt;p&gt;To get accurate shipping information &lt;a href=&quot;/user/address&quot;&gt;Edit Location&lt;/a&gt;&lt;/p&gt;&lt;p&gt;&lt;strong&gt;COMFORT, HERITAGE. NOTHING BETTER.&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Nothing as fly, nothing as comfortable, nothing as proven. The Nike Air Max 90 stays true to its OG running roots with the iconic Waffle sole, stitched overlays and classic TPU details. Classic colors celebrate your fresh look while Max Air cushioning adds comfort to the journey.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Performance Comfort&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Originally designed for performance running, the Max Air unit in the heel adds unbelievable cushioning.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Versatile Style&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The low-top design combines with a padded collar for a sleek look that feels soft and comfortable.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Retro Vibes&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The stitched overlays and TPU accents add durability, comfort and the iconic \'90s look you love.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Textile upper with leather and synthetic overlays&lt;/li&gt;&lt;li&gt;Foam midsole&lt;/li&gt;&lt;li&gt;Rubber Waffle outsole adds traction and durability&lt;ul&gt;&lt;li&gt;Shown: Wolf Grey/Black/White/Black&lt;/li&gt;&lt;li&gt;Style: CN8490-001&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Nike Air Max Origins&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Revolutionary Air technology first made its way into Nike footwear in 1978. In 1987, the Air Max 1 debuted with visible Air technology in its heel, allowing fans more than just the feel of Air cushioning&mdash;suddenly they could see it. Since then, next-generation Air Max shoes have become a hit with athletes and collectors by offering striking color combinations and reliable, lightweight cushioning.&lt;/p&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704270012/njsatpli7rviso6zqtp1.png','Nothing as fly, nothing as comfortable, nothing as proven.',130.0,NULL,NULL,4,'2024-01-03 08:20:11','2024-01-03 08:20:11',false),(12,'Jordan Spizike Low','&lt;p&gt;&lt;strong&gt;Free Shipping*&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;To get accurate shipping information &lt;a href=&quot;/user/address&quot;&gt;Edit Location&lt;/a&gt;&lt;/p&gt;&lt;p&gt;The Spizike takes elements of four classic Jordans, combines them, and gives you one iconic sneaker. It\'s an homage to Spike Lee formally introducing Hollywood and hoops in a culture moment. You get a great looking pair of kicks with some history. What more can you ask for? Ya dig?&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Benefits&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Visible Nike Air-Sole unit provides lightweight cushioning.&lt;/li&gt;&lt;li&gt;Rubber outsole gives you ample traction.&lt;/li&gt;&lt;/ul&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;Not intended for use as Personal Protective Equipment (PPE)&lt;ul&gt;&lt;li&gt;Shown: Legion Green/Military Brown/University Red/Black&lt;/li&gt;&lt;li&gt;Style: FD4653-300&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704270190/ucfxuf0zmifaezmbitp0.png','The Spizike takes elements of four classic Jordans, combines them, and gives you one iconic sneaker.',150.0,NULL,NULL,5,'2024-01-03 08:23:10','2024-01-03 08:23:10',false),(13,'Air Jordan 1 High G NRG','&lt;p&gt;&lt;strong&gt;Free Shipping*&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;To get accurate shipping information &lt;a href=&quot;/user/address&quot;&gt;Edit Location&lt;/a&gt;&lt;/p&gt;&lt;p&gt;&amp;nbsp;&lt;/p&gt;&lt;p&gt;One of the reasons why MJ loves golf so much: It&rsquo;s like looking into a mirror. The only opponent he sees is staring right back at him. No teammates to play off. No defender to go at. Just him, his swing and the course. This special AJ1 High, with its metallic finishes, chrome aglets and glossy Air Jordan details, takes inspiration from the internal drive that inspires both MJ and us to keep getting better every day.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Original AJ1 Design&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Real and synthetic leather and bold color-blocking recreate the classic look.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Air Cushioning&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Encapsulated Air in the heel cushions every step.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Heritage Traction&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Based on the design of the original outsole, the rubber-integrated traction pattern includes a forefoot pivot circle.&lt;/p&gt;&lt;p&gt;&lt;strong&gt;Product Details&lt;/strong&gt;&lt;/p&gt;&lt;ul&gt;&lt;li&gt;1-year waterproof warranty&lt;/li&gt;&lt;li&gt;2 sets of laces&lt;ul&gt;&lt;li&gt;Shown: Metallic Silver/Photon Dust/White/Metallic Silver&lt;/li&gt;&lt;li&gt;Style: FD6815-001&lt;/li&gt;&lt;/ul&gt;&lt;/li&gt;&lt;/ul&gt;','http://res.cloudinary.com/da5wewzih/image/upload/v1704270515/vzqfnuxgyczzazte698u.png','One of the reasons why MJ loves golf so much: It&rsquo;s like looking into a mirror.',210.0,NULL,NULL,4,'2024-01-03 08:28:34','2024-01-03 08:28:34',false);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productimage`
--

DROP TABLE IF EXISTS `productimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productimage` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `imageUrl` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                `productId` bigint DEFAULT NULL,
                                `createAt` timestamp NULL DEFAULT (now()),
                                `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                                PRIMARY KEY (`id`),
                                KEY `ProductImage_fk0` (`productId`),
                                CONSTRAINT `ProductImage_fk0` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productimage`
--

LOCK TABLES `productimage` WRITE;
/*!40000 ALTER TABLE `productimage` DISABLE KEYS */;
INSERT INTO `productimage` VALUES (1,'http://res.cloudinary.com/da5wewzih/image/upload/v1704212062/tud3vdjpcvo3xduowkki.webp',1,'2024-01-02 16:14:22','2024-01-02 16:14:22',false),(2,'http://res.cloudinary.com/da5wewzih/image/upload/v1704212065/d9lzteivb6jvhloba09t.webp',1,'2024-01-02 16:14:25','2024-01-02 16:14:25',false),(3,'http://res.cloudinary.com/da5wewzih/image/upload/v1704212068/bdtrn5m3yauabs0auwzu.jpg',1,'2024-01-02 16:14:28','2024-01-02 16:14:28',false),(5,'http://res.cloudinary.com/da5wewzih/image/upload/v1704215359/no6sjoahkjvgvzqnypte.webp',2,'2024-01-02 17:09:19','2024-01-02 17:09:19',false),(6,'http://res.cloudinary.com/da5wewzih/image/upload/v1704215362/sbdjsryaqkczs9ouyaeb.webp',2,'2024-01-02 17:09:22','2024-01-02 17:09:22',false),(7,'http://res.cloudinary.com/da5wewzih/image/upload/v1704215365/ims67scqedezkkjevsya.webp',2,'2024-01-02 17:09:24','2024-01-02 17:09:24',false),(8,'http://res.cloudinary.com/da5wewzih/image/upload/v1704215367/v4wa22furczvc5zj5ed6.webp',2,'2024-01-02 17:09:27','2024-01-02 17:09:27',false),(9,'http://res.cloudinary.com/da5wewzih/image/upload/v1704233335/mcq62nibcg9aebukyikx.png',3,'2024-01-02 22:08:55','2024-01-02 22:08:55',false),(10,'http://res.cloudinary.com/da5wewzih/image/upload/v1704233341/c9aqzef3nflaxp5b3qgq.png',3,'2024-01-02 22:09:01','2024-01-02 22:09:01',false),(11,'http://res.cloudinary.com/da5wewzih/image/upload/v1704233693/ay0yiik1p3gtzr8qph8y.png',4,'2024-01-02 22:15:00','2024-01-02 22:15:00',false),(12,'http://res.cloudinary.com/da5wewzih/image/upload/v1704233707/ujlef0wvnlbqmzjmtpxr.png',4,'2024-01-02 22:15:15','2024-01-02 22:15:15',false),(13,'http://res.cloudinary.com/da5wewzih/image/upload/v1704234104/ayvcche62iszn9gbqsxn.png',5,'2024-01-02 22:21:44','2024-01-02 22:21:44',false),(14,'http://res.cloudinary.com/da5wewzih/image/upload/v1704268743/nglns6zs8lh6fr2mpsar.png',7,'2024-01-03 07:59:03','2024-01-03 07:59:03',false),(15,'http://res.cloudinary.com/da5wewzih/image/upload/v1704268747/dxyghctoerajrh9qebty.png',7,'2024-01-03 07:59:06','2024-01-03 07:59:06',false),(16,'http://res.cloudinary.com/da5wewzih/image/upload/v1704268750/e31sfo9gpvxmirpopgms.png',7,'2024-01-03 07:59:09','2024-01-03 07:59:09',false),(17,'http://res.cloudinary.com/da5wewzih/image/upload/v1704268754/b4uafpa8odfhxcanqx4j.png',7,'2024-01-03 07:59:13','2024-01-03 07:59:13',false),(18,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269229/viqoo2otlqc4tbfcxpdu.png',8,'2024-01-03 08:07:09','2024-01-03 08:07:09',false),(19,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269233/p5b9mdrfl18e3vb4d5zk.png',8,'2024-01-03 08:07:13','2024-01-03 08:07:13',false),(20,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269237/d9dxsjdtvwot9bjuqvtq.png',8,'2024-01-03 08:07:16','2024-01-03 08:07:16',false),(21,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269239/dw4kkchcfhb8c4tf9swd.jpg',8,'2024-01-03 08:07:19','2024-01-03 08:07:19',false),(22,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269659/inqwkloa7kbldec3e9i6.png',9,'2024-01-03 08:14:18','2024-01-03 08:14:18',false),(23,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269662/wifzbnkbnldppiuq7pfx.png',9,'2024-01-03 08:14:21','2024-01-03 08:14:21',false),(24,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269665/brh9alf2n6xlewb58zm3.png',9,'2024-01-03 08:14:24','2024-01-03 08:14:24',false),(25,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269669/ryem7cnz69qci1jj7dc6.png',9,'2024-01-03 08:14:29','2024-01-03 08:14:29',false),(26,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269815/ansruelqmpfxxvtmohlh.jpg',10,'2024-01-03 08:16:55','2024-01-03 08:16:55',false),(27,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269819/bmp8jxw0sghq1cnxlptp.png',10,'2024-01-03 08:16:58','2024-01-03 08:16:58',false),(28,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269822/hrf99ku4d0kzgskr6nqw.png',10,'2024-01-03 08:17:01','2024-01-03 08:17:01',false),(29,'http://res.cloudinary.com/da5wewzih/image/upload/v1704269825/bizm5uhyidrr0cbv3cg7.png',10,'2024-01-03 08:17:05','2024-01-03 08:17:05',false),(30,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270015/gcrgvjswi9edw6ahwlnl.png',11,'2024-01-03 08:20:15','2024-01-03 08:20:15',false),(31,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270019/j51ih79g25jstnrzgplg.png',11,'2024-01-03 08:20:18','2024-01-03 08:20:18',false),(32,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270023/tvetag7hynmzzjxf8bs8.png',11,'2024-01-03 08:20:22','2024-01-03 08:20:22',false),(33,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270026/rlnarvzyezg7nv1hq1cr.png',11,'2024-01-03 08:20:26','2024-01-03 08:20:26',false),(34,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270194/nokr31avojva7z85nwof.png',12,'2024-01-03 08:23:14','2024-01-03 08:23:14',false),(35,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270198/gratmjohlb8iw5cxkvth.png',12,'2024-01-03 08:23:17','2024-01-03 08:23:17',false),(36,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270201/jdsgvumbcjlwjojhznos.png',12,'2024-01-03 08:23:20','2024-01-03 08:23:20',false),(37,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270204/phhorvcbk7ano1dviyqy.jpg',12,'2024-01-03 08:23:23','2024-01-03 08:23:23',false),(38,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270518/fgotplqnjlr9ybsjtntd.jpg',13,'2024-01-03 08:28:37','2024-01-03 08:28:37',false),(39,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270521/ta793baehabbjuitflqc.jpg',13,'2024-01-03 08:28:40','2024-01-03 08:28:40',false),(40,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270524/etuas4oioeiekancagio.jpg',13,'2024-01-03 08:28:44','2024-01-03 08:28:44',false),(41,'http://res.cloudinary.com/da5wewzih/image/upload/v1704270527/j6ac5uvwlw1phf5uqdch.jpg',13,'2024-01-03 08:28:47','2024-01-03 08:28:47',false);
/*!40000 ALTER TABLE `productimage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productsize`
--

DROP TABLE IF EXISTS `productsize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productsize` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `sizeId` bigint DEFAULT NULL,
                               `productId` bigint DEFAULT NULL,
                               `createAt` timestamp NULL DEFAULT (now()),
                               `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                               PRIMARY KEY (`id`),
                               KEY `ProductSize_fk0` (`sizeId`),
                               KEY `ProductSize_fk1` (`productId`),
                               CONSTRAINT `ProductSize_fk0` FOREIGN KEY (`sizeId`) REFERENCES `size` (`id`),
                               CONSTRAINT `ProductSize_fk1` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productsize`
--

LOCK TABLES `productsize` WRITE;
/*!40000 ALTER TABLE `productsize` DISABLE KEYS */;
INSERT INTO `productsize` VALUES (1,22,1,'2024-01-02 16:14:19','2024-01-02 16:14:19',false),(2,23,1,'2024-01-02 16:14:19','2024-01-02 16:14:19',false),(3,24,1,'2024-01-02 16:14:19','2024-01-02 16:14:19',false),(4,25,1,'2024-01-02 16:14:19','2024-01-02 16:14:19',false),(5,1,2,'2024-01-02 17:09:17','2024-01-02 17:09:17',false),(6,2,2,'2024-01-02 17:09:17','2024-01-02 17:09:17',false),(7,3,2,'2024-01-02 17:09:17','2024-01-02 17:09:17',false),(8,4,2,'2024-01-02 17:09:17','2024-01-02 17:09:17',false),(9,5,2,'2024-01-02 17:09:17','2024-01-02 17:09:17',false),(10,6,2,'2024-01-02 17:09:17','2024-01-02 17:09:17',false),(11,2,3,'2024-01-02 22:08:47','2024-01-02 22:08:47',false),(12,3,3,'2024-01-02 22:08:50','2024-01-02 22:08:50',false),(13,4,3,'2024-01-02 22:08:50','2024-01-02 22:08:50',false),(14,5,3,'2024-01-02 22:08:50','2024-01-02 22:08:50',false),(15,6,3,'2024-01-02 22:08:50','2024-01-02 22:08:50',false),(16,5,4,'2024-01-02 22:14:43','2024-01-02 22:14:43',false),(17,6,4,'2024-01-02 22:14:43','2024-01-02 22:14:43',false),(18,7,4,'2024-01-02 22:14:43','2024-01-02 22:14:43',false),(19,8,4,'2024-01-02 22:14:43','2024-01-02 22:14:43',false),(20,9,4,'2024-01-02 22:14:43','2024-01-02 22:14:43',false),(21,1,5,'2024-01-02 22:20:50','2024-01-02 22:20:50',false),(22,2,5,'2024-01-02 22:20:50','2024-01-02 22:20:50',false),(23,3,5,'2024-01-02 22:20:50','2024-01-02 22:20:50',false),(25,19,7,'2024-01-03 07:58:59','2024-01-03 07:58:59',false),(26,20,7,'2024-01-03 07:58:59','2024-01-03 07:58:59',false),(27,21,7,'2024-01-03 07:58:59','2024-01-03 07:58:59',false),(28,22,7,'2024-01-03 07:58:59','2024-01-03 07:58:59',false),(29,1,8,'2024-01-03 08:07:05','2024-01-03 08:07:05',false),(30,2,8,'2024-01-03 08:07:05','2024-01-03 08:07:05',false),(31,3,8,'2024-01-03 08:07:05','2024-01-03 08:07:05',false),(32,4,8,'2024-01-03 08:07:05','2024-01-03 08:07:05',false),(33,5,8,'2024-01-03 08:07:05','2024-01-03 08:07:05',false),(34,6,8,'2024-01-03 08:07:05','2024-01-03 08:07:05',false),(35,1,9,'2024-01-03 08:14:14','2024-01-03 08:14:14',false),(36,2,9,'2024-01-03 08:14:14','2024-01-03 08:14:14',false),(37,3,9,'2024-01-03 08:14:14','2024-01-03 08:14:14',false),(38,4,9,'2024-01-03 08:14:14','2024-01-03 08:14:14',false),(39,19,10,'2024-01-03 08:16:52','2024-01-03 08:16:52',false),(40,20,10,'2024-01-03 08:16:52','2024-01-03 08:16:52',false),(41,21,10,'2024-01-03 08:16:52','2024-01-03 08:16:52',false),(42,22,10,'2024-01-03 08:16:52','2024-01-03 08:16:52',false),(43,23,10,'2024-01-03 08:16:52','2024-01-03 08:16:52',false),(44,24,10,'2024-01-03 08:16:52','2024-01-03 08:16:52',false),(45,1,11,'2024-01-03 08:20:11','2024-01-03 08:20:11',false),(46,2,11,'2024-01-03 08:20:11','2024-01-03 08:20:11',false),(47,3,11,'2024-01-03 08:20:11','2024-01-03 08:20:11',false),(48,4,11,'2024-01-03 08:20:11','2024-01-03 08:20:11',false),(49,1,12,'2024-01-03 08:23:10','2024-01-03 08:23:10',false),(50,2,12,'2024-01-03 08:23:10','2024-01-03 08:23:10',false),(51,3,12,'2024-01-03 08:23:10','2024-01-03 08:23:10',false),(52,4,12,'2024-01-03 08:23:10','2024-01-03 08:23:10',false),(53,1,13,'2024-01-03 08:28:34','2024-01-03 08:28:34',false),(54,2,13,'2024-01-03 08:28:34','2024-01-03 08:28:34',false),(55,3,13,'2024-01-03 08:28:34','2024-01-03 08:28:34',false),(56,4,13,'2024-01-03 08:28:34','2024-01-03 08:28:34',false);
/*!40000 ALTER TABLE `productsize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                        `createAt` timestamp NULL DEFAULT (now()),
                        `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'M 7 / W 8.5','2024-01-01 20:31:05','2024-01-01 20:31:05',false),(2,'M 7.5 / W 9','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(3,'M 8 / W 9.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(4,'M 8.5 / W 10','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(5,'M 9 / W 10.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(6,'M 9.5 / W 11','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(7,'M 10 / W 11.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(8,'M 10.5 / W 12','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(9,'M 11 / W 12.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(10,'M 11.5 / W 13','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(11,'M 12 / W 13.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(12,'M 12.5 / W 14','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(13,'M 13 / W 14.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(14,'M 14 / W 15.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(15,'M 15 / W 16.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(16,'M 16 / W 17.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(17,'M 17 / W 18.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(18,'M 18 / W 19.5','2024-01-01 20:31:06','2024-01-01 20:31:06',false),(19,'M 3.5 / W 5','2024-01-01 20:34:41','2024-01-01 20:34:41',false),(20,'M 4 / W 5.5','2024-01-01 20:34:41','2024-01-01 20:34:41',false),(21,'M 4.5 / W 6','2024-01-01 20:34:41','2024-01-01 20:34:41',false),(22,'M 5 / W 6.5','2024-01-01 20:34:41','2024-01-01 20:34:41',false),(23,'M 5.5 / W 7','2024-01-01 20:34:41','2024-01-01 20:34:41',false),(24,'M 6 / W 7.5','2024-01-01 20:34:41','2024-01-01 20:34:41',false),(25,'M 6.5 / W 8','2024-01-01 20:34:41','2024-01-01 20:34:41',false);
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `userName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                        `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                        `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                        `fullName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                        `birthDay` date DEFAULT NULL,
                        `admin` tinyint(1) DEFAULT '0',
                        `association` varchar(20) DEFAULT 'none',
                        `createAt` timestamp NULL DEFAULT (now()),
                        `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `email` (`email`),
                        UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (6,'hoanghuydev','tranvohoanghuy12ab@gmail.com','$2a$12$n1ZRw5rmlRGU0uqK6USVR.p8i66degNqeanwUZK9j0hdgDF8gfvVm','Tran Vo Hoang Huy',NULL,1,'none','2023-12-27 08:23:38',NULL,false),(8,'111635119529567317993','21130386@st.hcmuaf.edu.vn',NULL,'Trần Võ Hoàng Huy',NULL,0,'google','2023-12-28 04:21:13',NULL,false);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraddress`
--

DROP TABLE IF EXISTS `useraddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `useraddress` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `addressId` bigint DEFAULT NULL,
                               `userId` bigint DEFAULT NULL,
                               `createAt` timestamp NULL DEFAULT (now()),
                               `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                               PRIMARY KEY (`id`),
                               KEY `UserAddress_fk0` (`addressId`),
                               KEY `UserAddress_fk1` (`userId`),
                               CONSTRAINT `UserAddress_fk0` FOREIGN KEY (`addressId`) REFERENCES `address` (`id`),
                               CONSTRAINT `UserAddress_fk1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraddress`
--

LOCK TABLES `useraddress` WRITE;
/*!40000 ALTER TABLE `useraddress` DISABLE KEYS */;
/*!40000 ALTER TABLE `useraddress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userorder`
--

DROP TABLE IF EXISTS `userorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userorder` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `userId` bigint DEFAULT NULL,
                             `orderId` bigint DEFAULT NULL,
                             `createAt` timestamp NULL DEFAULT (now()),
                             `updateAt` timestamp NULL DEFAULT (now()),`isDeleted` tinyint(1) DEFAULT '0',
                             PRIMARY KEY (`id`),
                             KEY `UserOrder_fk0` (`userId`),
                             KEY `UserOrder_fk1` (`orderId`),
                             CONSTRAINT `UserOrder_fk0` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
                             CONSTRAINT `UserOrder_fk1` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userorder`
--

LOCK TABLES `userorder` WRITE;
/*!40000 ALTER TABLE `userorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `userorder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-03 21:59:54
