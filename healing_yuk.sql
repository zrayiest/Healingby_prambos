-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 02, 2025 at 02:22 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `healing_yuk`
--

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE `locations` (
  `id_location` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `short_description` text DEFAULT NULL,
  `category` varchar(100) NOT NULL,
  `full_description` text DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `operating_hours` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`id_location`, `name`, `image_url`, `short_description`, `category`, `full_description`, `address`, `operating_hours`) VALUES
(1, 'Candi Borobudur', 'https://fatek.umsu.ac.id/wp-content/uploads/2023/06/Candi-Borobudur-Makna-Yang-Terkandung-di-Dalamnya.jpg', 'Candi Buddha terbesar di dunia, sebuah mahakarya warisan UNESCO.', 'Wisata Sejarah', 'Terletak di Magelang, Jawa Tengah, Candi Borobudur adalah monumen Buddha megah yang dibangun pada abad ke-9. Candi ini memiliki ribuan panel relief dan ratusan arca Buddha, menjadikannya tujuan ziarah dan wisata populer.', 'Jl. Badrawati, Kw. Candi Borobudur, Borobudur, Magelang, Jawa Tengah', '07:30 - 16:30 WIB'),
(2, 'Kepulauan Raja Ampat', 'https://upload.wikimedia.org/wikipedia/commons/8/88/Raja_Ampat%2C_Mutiara_Indah_di_Timur_Indonesia.jpg', 'Gugusan pulau eksotis dengan keindahan bawah laut yang menakjubkan.', 'Wisata Alam', 'Kepulauan Raja Ampat di Papua Barat adalah surga bagi para penyelam dan pecinta alam. Dikenal dengan perairan jernih, terumbu karang yang kaya, dan gugusan pulau karst (pianemo) yang ikonik.', 'Kabupaten Raja Ampat, Papua Barat Daya, Indonesia', '24 Jam (Akses pulau terbatas)'),
(3, 'Taman Mini Indonesia Indah (TMII)', 'https://tamanmini.com/taman_jelajah_indonesia/wp-content/uploads/2023/07/banner-4.jpg', 'Rangkuman kebudayaan Indonesia dalam satu kawasan wisata edukatif.', 'Wisata Budaya', 'TMII menampilkan anjungan dari berbagai provinsi di Indonesia, lengkap dengan arsitektur, pakaian adat, dan tradisinya. Selain itu, terdapat berbagai museum, teater, dan wahana rekreasi.', 'Jl. Taman Mini I, Ceger, Cipayung, Kota Jakarta Timur, DKI Jakarta', '08:00 - 17:00 WIB'),
(4, 'Kawah Ijen', 'https://www.yukbanyuwangi.co.id/wp-content/uploads/2023/12/DJI_0126_1200.jpg', 'Kawah vulkanik dengan fenomena api biru (blue fire) yang langka.', 'Wisata Alam', 'Terletak di perbatasan Banyuwangi dan Bondowoso, Kawah Ijen terkenal dengan danau kawah berwarna pirus dan fenomena api biru yang hanya bisa dilihat pada malam hari. Pendakian malam hari sangat populer di kalangan wisatawan.', 'Perbatasan Kab. Banyuwangi & Kab. Bondowoso, Jawa Timur', 'Pendakian: 02:00 - 12:00 WIB'),
(5, 'Dunia Fantasi (Dufan)', 'https://s-light.tiket.photos/t/01E25EBZS3W0FY9GTG6C42E1SE/rsfit1440960gsm/events/2024/12/29/591e6cad-919d-4c23-95a5-0a7f6d2b866a-1735466532176-da6a7edce3a96e416b7f994f5bf87670.png', 'Taman hiburan terbesar dan terpopuler di Jakarta dengan berbagai wahana.', 'Taman Hiburan', 'Bagian dari kompleks Taman Impian Jaya Ancol, Dufan menawarkan berbagai wahana pemacu adrenalin seperti Halilintar dan Kora-Kora, serta wahana keluarga yang menyenangkan. Merupakan destinasi favorit untuk rekreasi.', 'Jl. Lodan Timur No.7, Ancol, Pademangan, Jakarta Utara, DKI Jakarta', '10:00 - 18:00 WIB'),
(10, 'Drama Coffee & Roastery', 'https://lh3.googleusercontent.com/gps-cs-s/AC9h4no2sQh9-eKUUUhobyUWkf76Wz8yO8Q9LKGcz47ad66oKNENuhrmgTgL6tsmRIbAvJCDXoB_Sv3dQFydlNFBwUzah80Q2Uu-tyw-uZBwl1rZefYNUwQSqlnkHnS-0Dl0ZJGxK44m=s1360-w1360-h1020-rw', 'Place for people hangout', 'Cafe', 'none', 'Jl. Manyar Kertoarjo III No.87, RT.003/RW.06, Manyar Sabrangan, Kec. Mulyorejo, Surabaya, Jawa Timur 60116', '07.00 am - 10.00 pm');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `email`, `name`, `password`, `created_at`) VALUES
(1, 'test@gmail.com', 'user1', '123', '2025-06-30 17:03:40'),
(2, 'Jimi', 'jimi@gmail.com', '123', '2025-07-02 04:36:29'),
(5, 'jimi@gmail.com', 'jimi', '123', '0000-00-00 00:00:00'),
(6, 'pengguna.baru@gmail.com', 'Pengguna Baru', '789789', '2025-07-02 07:05:45');

-- --------------------------------------------------------

--
-- Table structure for table `user_favorites`
--

CREATE TABLE `user_favorites` (
  `id_user` int(11) NOT NULL,
  `id_location` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_favorites`
--

INSERT INTO `user_favorites` (`id_user`, `id_location`) VALUES
(1, 1),
(1, 2),
(2, 1),
(5, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`id_location`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `user_favorites`
--
ALTER TABLE `user_favorites`
  ADD PRIMARY KEY (`id_user`,`id_location`),
  ADD KEY `id_location` (`id_location`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `locations`
--
ALTER TABLE `locations`
  MODIFY `id_location` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_favorites`
--
ALTER TABLE `user_favorites`
  ADD CONSTRAINT `user_favorites_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_favorites_ibfk_2` FOREIGN KEY (`id_location`) REFERENCES `locations` (`id_location`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
