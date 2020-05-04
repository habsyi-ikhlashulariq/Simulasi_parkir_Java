-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 25 Mei 2017 pada 11.08
-- Versi Server: 10.1.13-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parkir1db`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `data`
--

CREATE TABLE `data` (
  `No_parkir` varchar(10) NOT NULL,
  `No_polisi` varchar(15) NOT NULL,
  `nama` varchar(15) NOT NULL,
  `Jenis_kendaraan` varchar(10) NOT NULL,
  `tarif` int(11) NOT NULL,
  `masuk` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `data`
--

INSERT INTO `data` (`No_parkir`, `No_polisi`, `nama`, `Jenis_kendaraan`, `tarif`, `masuk`) VALUES
('001', 'K100YT', 'Pam', 'Roda 2', 2000, '17:59:57');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_keluar`
--

CREATE TABLE `tb_keluar` (
  `No_parkir` varchar(10) NOT NULL,
  `No_polisi` varchar(15) NOT NULL,
  `nama` varchar(15) NOT NULL,
  `Jenis_kendaraan` varchar(10) NOT NULL,
  `tarif` int(11) NOT NULL,
  `masuk` time NOT NULL,
  `id_keluar` varchar(10) NOT NULL,
  `keluar` time NOT NULL,
  `lama` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
