-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 03, 2025 at 02:32 PM
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
(1, 'Kopi Es Tak Kie', 'https://lh3.googleusercontent.com/p/AF1QipN4suChrkEKhsCPEwqQkqBJaeBEI3yTLSQQblv7=s1360-w1360-h1020-rw', 'Kedai kopi legendaris di Glodok, Jakarta, yang telah berdiri sejak tahun 1927.', 'Cafe', 'Sebagai salah satu kedai kopi tertua di Jakarta, Kopi Es Tak Kie menawarkan suasana nostalgia yang kental. Menu andalannya adalah es kopi susu sederhana yang resepnya tidak berubah selama puluhan tahun. Tempat ini menjadi favorit banyak kalangan untuk menikmati kopi sambil merasakan atmosfer Jakarta tempo dulu.', 'Jl. Pintu Besar Selatan III No.4-6, Pinangsia, Taman Sari, Kota Jakarta Barat, Jakarta', 'Selasa-Minggu: 06:30 - 14:00'),
(2, 'Hotel Mulia Senayan', 'https://lh3.googleusercontent.com/proxy/TSGyKfVyU25YI8_My1repBZtI1H1Y3xoA5FIOIrUsHw4oIw9KQpaYZRAx3QxV5Fe8HZoS5pBZMubExAZoQGjYiADK49uSwdsExUD3GBtm1eDShnxDy4Y37Jk9pKhE60W0MKpobksgu-kA5SiX4zawypowDvdf6U=s1360-w1360-h1020-rw', 'Hotel bintang 5 yang mewah di pusat kota Jakarta dengan fasilitas premium dan akses mudah ke area bisnis.', 'Hotel', 'Hotel Mulia Senayan menawarkan kemewahan dan pelayanan kelas dunia. Memiliki kamar dan suite yang elegan, berbagai restoran fine dining internasional, kolam renang, pusat kebugaran, dan spa. Lokasinya yang strategis menjadikannya pilihan utama bagi pebisnis dan wisatawan.', 'Jl. Asia Afrika Senayan, Gelora, Tanah Abang, Kota Jakarta Pusat, Jakarta', '24 Jam'),
(3, 'Gunung Bromo', 'https://encrypted-tbn1.gstatic.com/licensed-image?q=tbn:ANd9GcQcyt9jQFFzGLGbwq-biUXNvB2P7OJ_Wsczt2C4BhntckM_cds33uL89Xqbvay-LS9YsT_QwOitsHuoSYdi9MwT9r14iTlc6kjaWjQkNQ', 'Gunung berapi aktif di Jawa Timur yang terkenal dengan pemandangan matahari terbitnya yang magis di atas lautan pasir.', 'Wisata Alam', 'Gunung Bromo adalah bagian dari Taman Nasional Bromo Tengger Semeru. Daya tarik utamanya adalah menyaksikan matahari terbit dari Puncak Penanjakan, dengan pemandangan kaldera, kawah Bromo yang masih aktif, dan Gunung Semeru di kejauhan. Pengalaman menunggang kuda atau berjalan kaki di lautan pasir juga sangat populer.', 'Taman Nasional Bromo Tengger Semeru, Jawa Timur, Indonesia', '24 Jam (area taman nasional)');

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
(1, 'user1@gmail.com', 'User1', '123', '2025-07-03 19:23:55'),
(2, 'user2@gmail.com', 'user2', '1234', '2025-07-03 19:27:00');

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
(2, 1);

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
  MODIFY `id_location` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
