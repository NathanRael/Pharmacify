-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2024 at 07:19 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pharmacify`
--

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

CREATE TABLE `delivery` (
  `delId` int(10) NOT NULL,
  `delPrice` float NOT NULL,
  `delDate` date NOT NULL,
  `supId` int(10) NOT NULL,
  `medId` int(10) NOT NULL,
  `delQuantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `delivery`
--

INSERT INTO `delivery` (`delId`, `delPrice`, `delDate`, `supId`, `medId`, `delQuantity`) VALUES
(54, 1200, '2024-06-07', 3, 44, 50),
(55, 1200, '2024-06-07', 5, 45, 50),
(56, 2100, '2024-06-07', 7, 46, 80),
(57, 200, '2024-06-08', 3, 46, 30),
(58, 100, '2024-06-08', 3, 46, 10);

-- --------------------------------------------------------

--
-- Table structure for table `medicament`
--

CREATE TABLE `medicament` (
  `medId` int(10) NOT NULL,
  `medName` char(30) NOT NULL,
  `medDesc` char(230) NOT NULL,
  `medPrice` float NOT NULL,
  `medQuantity` int(10) NOT NULL,
  `stockId` int(10) NOT NULL,
  `medExpDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `medicament`
--

INSERT INTO `medicament` (`medId`, `medName`, `medDesc`, `medPrice`, `medQuantity`, `stockId`, `medExpDate`) VALUES
(44, 'Amoxicilline', 'Un antibiotique utilisé pour traiter diverses infections', 2000, 28, 1, '2026-10-24'),
(45, 'Paracétamole', 'Utilidé pour soulager ...', 1000, 46, 1, '2026-12-24'),
(46, 'Ibuprofène', 'Un anti-inflammatoire', 1500, 68, 1, '2026-10-24'),
(47, 'Oméprazole', 'Utiliser pour traiter', 2500, 0, 1, '2024-08-07'),
(48, 'Loratadine', 'Un anthistaminique', 1200, 0, 1, '2024-08-07'),
(49, 'Metrofomine', 'Pour traiter la diabète', 3000, 0, 1, '2024-08-07');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `patientId` int(10) NOT NULL,
  `patientFName` char(30) NOT NULL,
  `patientLName` char(30) NOT NULL,
  `patientPhone` char(10) NOT NULL,
  `patientAddress` char(30) NOT NULL,
  `patientEmail` char(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`patientId`, `patientFName`, `patientLName`, `patientPhone`, `patientAddress`, `patientEmail`) VALUES
(4, 'Raels', 'Nathan', '0388732917', 'Andrainjato', 'ralaivoavy.natanael@gmail.com'),
(5, 'Zuckerberg', 'Steves', '0381234567', 'Andrainjato', 'steve@gmail.com'),
(7, 'Audric', 'Brian', '0388732918', 'Andrainjato', 'audric@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `prescription`
--

CREATE TABLE `prescription` (
  `prescId` int(10) NOT NULL,
  `prescDate` datetime NOT NULL,
  `prescDuration` char(4) NOT NULL,
  `prescDesc` text NOT NULL,
  `patientId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `prescription`
--

INSERT INTO `prescription` (`prescId`, `prescDate`, `prescDuration`, `prescDesc`, `patientId`) VALUES
(21, '2024-06-07 19:29:42', '7', 'Paracétamole=>2:3:2:18;Ibuprofène=>1:2:1:18;', 4),
(22, '2024-06-07 19:46:51', '4', 'Amoxicilline=>1:1:1:18;', 4),
(23, '2024-06-08 10:36:50', '2', 'Ibuprofène=>1:1:1:3;', 4);

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `purchaseId` int(10) NOT NULL,
  `purchaseDate` datetime DEFAULT NULL,
  `medId` int(10) DEFAULT NULL,
  `patientId` int(10) DEFAULT NULL,
  `totalPrice` float NOT NULL,
  `purchaseQuantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`purchaseId`, `purchaseDate`, `medId`, `patientId`, `totalPrice`, `purchaseQuantity`) VALUES
(16, '2024-06-07 00:00:00', 44, 4, 4000, 2),
(17, '2024-06-07 00:00:00', 45, 4, 2000, 2),
(18, '2024-06-07 00:00:00', 46, 4, 1500, 1),
(19, '2024-06-08 00:00:00', 46, 4, 30000, 20),
(20, '2024-06-08 10:27:29', 46, 5, 15000, 10),
(21, '2024-06-08 10:36:55', 46, 4, 3000, 2),
(22, '2024-06-08 10:40:51', 46, 4, 3000, 2),
(23, '2024-06-08 10:51:08', 46, 4, 3000, 2),
(24, '2024-06-08 10:56:29', 44, 7, 40000, 20),
(25, '2024-06-08 12:01:21', 44, 4, 10000, 5),
(26, '2024-06-08 12:08:22', 46, 7, 6000, 4),
(27, '2024-06-08 12:10:51', 46, 4, 3000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `stockId` int(10) NOT NULL,
  `stockName` char(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stockId`, `stockName`) VALUES
(1, 'Defaut'),
(2, 'TWO');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supId` int(10) NOT NULL,
  `supName` char(30) NOT NULL,
  `supPhone` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supId`, `supName`, `supPhone`) VALUES
(3, 'clickMan', '0388732917'),
(5, 'Tockys', '0342123654'),
(7, 'SteveFrns', '0326412387');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userId` int(10) NOT NULL,
  `userName` char(30) NOT NULL,
  `userPhone` char(10) NOT NULL,
  `userPwd` char(30) NOT NULL,
  `stockId` int(10) DEFAULT 1,
  `userStatus` int(1) DEFAULT 0,
  `userRole` int(1) DEFAULT 0,
  `userEmail` char(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `userName`, `userPhone`, `userPwd`, `stockId`, `userStatus`, `userRole`, `userEmail`) VALUES
(1, 'Nathan', '0388732917', 'nathanrael', 1, 1, 1, 'ralaivoavy.natanael@gmail.com'),
(6, 'Mihaja', '0341234567', 'admin123', 1, 1, 0, 'rlvnatanael@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`delId`),
  ADD KEY `supId` (`supId`),
  ADD KEY `medId` (`medId`);

--
-- Indexes for table `medicament`
--
ALTER TABLE `medicament`
  ADD PRIMARY KEY (`medId`),
  ADD KEY `stockId` (`stockId`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patientId`),
  ADD UNIQUE KEY `unique_patient` (`patientFName`,`patientEmail`);

--
-- Indexes for table `prescription`
--
ALTER TABLE `prescription`
  ADD PRIMARY KEY (`prescId`),
  ADD KEY `patientId` (`patientId`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`purchaseId`),
  ADD KEY `medId` (`medId`),
  ADD KEY `patientId` (`patientId`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`stockId`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`),
  ADD KEY `stockId` (`stockId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `delivery`
--
ALTER TABLE `delivery`
  MODIFY `delId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `medicament`
--
ALTER TABLE `medicament`
  MODIFY `medId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `patientId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `prescription`
--
ALTER TABLE `prescription`
  MODIFY `prescId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `purchaseId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stockId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `prescription`
--
ALTER TABLE `prescription`
  ADD CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`patientId`) REFERENCES `patient` (`patientId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
