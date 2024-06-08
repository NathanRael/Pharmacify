-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2024 at 09:53 PM
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
(3, 200, '2024-05-27', 2, 8, 4),
(4, 200, '2024-05-27', 2, 8, 4),
(5, 200, '2024-05-27', 3, 8, 4),
(6, 300, '2024-05-28', 1, 4, 4),
(7, 300, '2024-05-29', 2, 9, 30),
(8, 200, '2024-05-27', 2, 7, 40),
(9, 500, '2024-05-29', 3, 7, 2),
(10, 200, '2024-05-29', 2, 8, 4),
(12, 300, '2024-05-30', 2, 11, 20),
(13, 300, '2024-05-30', 1, 11, 3),
(14, 300, '2024-05-30', 2, 12, 20),
(15, 300, '2024-05-30', 3, 13, 20),
(16, 300, '2024-05-30', 1, 11, 3),
(17, 1200, '2024-05-30', 2, 11, 10),
(18, 1200, '2024-05-30', 3, 12, 32),
(19, 300, '2024-06-02', 2, 11, 20),
(20, 300, '2024-06-02', 2, 12, 20);

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
(4, 'Raels', 'Nathan', '0388732917', 'Andrainjato', 'raels@gmail.com'),
(5, 'Zuckerberg', 'Steve', '0381234567', 'Andrainjato', 'steve@gmail.com'),
(6, 'Zuckerbergs', 'Steve', '0381234567', 'Andrainjato', 'zuck@gmail.com');

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
(13, '2024-05-30 05:59:19', '3', 'Paracetamol=>1:2:3;Efferalgant=>2:1:3;', 4),
(14, '2024-06-01 02:03:46', '3', 'Paracetamole=>1:3:2;Efferalgant=>2:6:4;', 4);

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `purchaseId` int(10) NOT NULL,
  `purchaseDate` date NOT NULL,
  `medId` int(10) DEFAULT NULL,
  `patientId` int(10) DEFAULT NULL,
  `totalPrice` float NOT NULL,
  `purchaseQuantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`purchaseId`, `purchaseDate`, `medId`, `patientId`, `totalPrice`, `purchaseQuantity`) VALUES
(5, '2024-05-30', 11, 4, 4800, 4),
(6, '2024-05-30', 11, 4, 6000, 5),
(7, '2024-05-30', 12, 4, 21600, 12),
(8, '2024-05-31', 10, 4, 30000, 2),
(9, '2024-06-02', 12, 5, 7200, 4);

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
(2, 'Oulaa', '0345678902'),
(3, 'admin', '0388732917'),
(5, 'NAthan', '0342123654'),
(6, 'Zuckerberge', '0326412387');

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
  `userRole` int(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `userName`, `userPhone`, `userPwd`, `stockId`, `userStatus`, `userRole`) VALUES
(1, 'Nathan', '0388732917', 'admin123', 1, 1, 1),
(3, 'NathanBlast', '0341234560', 'admin1234', 1, 1, 0),
(4, 'raels', '0388732917', 'admin123', 1, 1, 1);

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
  MODIFY `delId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `medicament`
--
ALTER TABLE `medicament`
  MODIFY `medId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `patientId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `prescription`
--
ALTER TABLE `prescription`
  MODIFY `prescId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `purchaseId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stockId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
