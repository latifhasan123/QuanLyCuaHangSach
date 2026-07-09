USE [master]
GO
/****** Object:  Database [QuanLyCuaHangSach]    Script Date: 3/15/2026 11:15:55 PM ******/
CREATE DATABASE [QuanLyCuaHangSach]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QuanLyCuaHangSach', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL17.SQLEXPRESS\MSSQL\DATA\QuanLyCuaHangSach.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QuanLyCuaHangSach_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL17.SQLEXPRESS\MSSQL\DATA\QuanLyCuaHangSach_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [QuanLyCuaHangSach] SET COMPATIBILITY_LEVEL = 170
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QuanLyCuaHangSach].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QuanLyCuaHangSach] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET ARITHABORT OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET  ENABLE_BROKER 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET  MULTI_USER 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QuanLyCuaHangSach] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [QuanLyCuaHangSach] SET OPTIMIZED_LOCKING = OFF 
GO
ALTER DATABASE [QuanLyCuaHangSach] SET QUERY_STORE = ON
GO
ALTER DATABASE [QuanLyCuaHangSach] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [QuanLyCuaHangSach]
GO
/****** Object:  Table [dbo].[ChiTietDonHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietDonHang](
	[MaCTDH] [int] IDENTITY(1,1) NOT NULL,
	[MaDH] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
	[DonGia] [decimal](18, 0) NOT NULL,
	[ThanhTien]  AS (isnull([SoLuong]*[DonGia],(0))) PERSISTED NOT NULL,
	[HanDoiTra] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCTDH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietGioHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietGioHang](
	[MaCTGH] [int] IDENTITY(1,1) NOT NULL,
	[MaGH] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCTGH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietPhieuNhap]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietPhieuNhap](
	[MaCTPN] [int] IDENTITY(1,1) NOT NULL,
	[MaPN] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
	[GiaNhap] [decimal](18, 0) NOT NULL,
	[ThanhTien]  AS (isnull([SoLuong]*[GiaNhap],(0))) PERSISTED NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCTPN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietPhieuTraKhachHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietPhieuTraKhachHang](
	[MaPTKH] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[SoLuongTra] [int] NOT NULL,
	[DonGiaTra] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaPTKH] ASC,
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietTraKhachHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietTraKhachHang](
	[MaCTPTK] [int] IDENTITY(1,1) NOT NULL,
	[MaPTK] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
	[TinhTrangSach] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCTPTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietTraNhaCungCap]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietTraNhaCungCap](
	[MaCTPTN] [int] IDENTITY(1,1) NOT NULL,
	[MaPTN] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCTPTN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChucVu]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChucVu](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaCV]  AS (isnull('CV'+right('00'+CONVERT([varchar](10),[MaID]),(2)),'')) PERSISTED NOT NULL,
	[TenChucVu] [nvarchar](100) NOT NULL,
	[MoTa] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DanhMuc]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMuc](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaDanhMuc]  AS ('DM'+right('000'+CONVERT([varchar](10),[MaID]),(3))) PERSISTED,
	[TenDanhMuc] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DonHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DonHang](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaDH]  AS (isnull('DH'+right('0000000'+CONVERT([varchar](10),[MaID]),(7)),'')) PERSISTED NOT NULL,
	[MaNV] [int] NULL,
	[MaKH] [int] NULL,
	[MaKM] [int] NULL,
	[TongTien] [decimal](18, 0) NOT NULL,
	[TienGiam] [decimal](18, 0) NULL,
	[ThanhTien] [decimal](18, 0) NOT NULL,
	[PhiGiaoHang] [decimal](18, 0) NULL,
	[LoaiDonHang] [varchar](20) NOT NULL,
	[TrangThaiDon] [varchar](20) NULL,
	[NgayTao] [datetime] NULL,
	[TenNguoiNhan] [nvarchar](100) NULL,
	[SDTNhan] [varchar](15) NULL,
	[DiaChiGiao] [nvarchar](max) NULL,
	[TrangThaiGiaoHang] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GioHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GioHang](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaGH]  AS (isnull('GH'+right('000000'+CONVERT([varchar](10),[MaID]),(6)),'')) PERSISTED NOT NULL,
	[MaKH] [int] NOT NULL,
	[NgayTao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaHD]  AS (isnull('HD'+right('0000000'+CONVERT([varchar](10),[MaID]),(7)),'')) PERSISTED NOT NULL,
	[MaDH] [int] NOT NULL,
	[HinhThucThanhToan] [nvarchar](50) NULL,
	[SoTienKhachTra] [decimal](18, 0) NOT NULL,
	[TienThua] [decimal](18, 0) NULL,
	[NgayThanhToan] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaKH]  AS (isnull('KH'+right('000000'+CONVERT([varchar](10),[MaID]),(6)),'')) PERSISTED NOT NULL,
	[MaTaiKhoanKH] [int] NULL,
	[HoTen] [nvarchar](100) NOT NULL,
	[SoDienThoai] [varchar](15) NOT NULL,
	[Email] [varchar](100) NULL,
	[DiaChi] [nvarchar](max) NULL,
	[DiemTichLuy] [int] NULL,
	[TrangThai] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhuyenMai]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhuyenMai](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaKM]  AS (isnull('KM'+right('0000'+CONVERT([varchar](10),[MaID]),(4)),'')) PERSISTED NOT NULL,
	[TenKM] [nvarchar](255) NOT NULL,
	[PhanTramGiam] [decimal](5, 2) NULL,
	[SoTienGiam] [decimal](18, 0) NULL,
	[DonHangToiThieu] [decimal](18, 0) NULL,
	[NgayBatDau] [date] NULL,
	[NgayKetThuc] [date] NULL,
	[TrangThai] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LichSuKho]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LichSuKho](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaLSK]  AS (isnull('LSK'+right('000000'+CONVERT([varchar](10),[MaID]),(6)),'')) PERSISTED NOT NULL,
	[MaSach] [int] NOT NULL,
	[LoaiGiaoDich] [varchar](50) NOT NULL,
	[MaChungTu] [int] NOT NULL,
	[SoLuongThayDoi] [int] NOT NULL,
	[SoLuongLoi] [int] NULL,
	[PhanLoaiTinhTrang] [nvarchar](100) NULL,
	[NgayGioTao] [datetime] NULL,
	[GhiChu] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhaCungCap]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhaCungCap](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaNCC]  AS (isnull('NCC'+right('0000'+CONVERT([varchar](10),[MaID]),(4)),'')) PERSISTED NOT NULL,
	[TenNCC] [nvarchar](255) NOT NULL,
	[SoDienThoai] [varchar](15) NULL,
	[DiaChi] [nvarchar](max) NULL,
	[TrangThai] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaNV]  AS (isnull('NV'+right('0000'+CONVERT([varchar](10),[MaID]),(4)),'')) PERSISTED NOT NULL,
	[MaChucVu] [int] NOT NULL,
	[MaTaiKhoanNV] [int] NOT NULL,
	[HoTen] [nvarchar](100) NOT NULL,
	[Email] [varchar](100) NULL,
	[SoDienThoai] [varchar](15) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhaXuatBan]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhaXuatBan](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaNXB]  AS ('NXB'+right('000'+CONVERT([varchar](10),[MaID]),(3))) PERSISTED,
	[TenNXB] [nvarchar](200) NOT NULL,
	[DiaChi] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhanQuyen]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhanQuyen](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaPQ]  AS (isnull('PQ'+right('00'+CONVERT([varchar](10),[MaID]),(2)),'')) PERSISTED NOT NULL,
	[TenQuyen] [nvarchar](100) NOT NULL,
	[MoTa] [nvarchar](max) NULL,
	[QlSach] [int] NULL,
	[QlThuocTinh] [int] NULL,
	[QlBanHang] [int] NULL,
	[QlKhuyenMai] [int] NULL,
	[QlHoaDon] [int] NULL,
	[QlPhieuDoiTra] [int] NULL,
	[QlNhapHang] [int] NULL,
	[QlPhieuNhap] [int] NULL,
	[QlKhachHang] [int] NULL,
	[QlNCC] [int] NULL,
	[QlNhanVien] [int] NULL,
	[QlTaiKhoan] [int] NULL,
	[QlPhanQuyen] [int] NULL,
	[QlThongKe] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuNhap]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuNhap](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaPN]  AS (isnull('PN'+right('000000'+CONVERT([varchar](10),[MaID]),(6)),'')) PERSISTED NOT NULL,
	[MaNV] [int] NULL,
	[MaNCC] [int] NOT NULL,
	[TongTien] [decimal](18, 0) NOT NULL,
	[TrangThai] [varchar](20) NULL,
	[NgayTao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuTraKhachHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuTraKhachHang](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaPTK]  AS (isnull('PTK'+right('000000'+CONVERT([varchar](10),[MaID]),(6)),'')) PERSISTED NOT NULL,
	[MaDH] [int] NOT NULL,
	[MaNV] [int] NOT NULL,
	[LyDo] [nvarchar](255) NULL,
	[TienHoan] [decimal](18, 0) NULL,
	[NgayTao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuTraNhaCungCap]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuTraNhaCungCap](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaPTN]  AS (isnull('PTN'+right('000000'+CONVERT([varchar](10),[MaID]),(6)),'')) PERSISTED NOT NULL,
	[MaNV] [int] NOT NULL,
	[MaNCC] [int] NOT NULL,
	[LyDo] [nvarchar](255) NOT NULL,
	[TongTienHoan] [decimal](18, 0) NULL,
	[NgayTao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sach]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaSach]  AS ('S'+right('000000'+CONVERT([varchar](10),[MaID]),(6))) PERSISTED,
	[TenSach] [nvarchar](255) NOT NULL,
	[ISBN] [varchar](13) NOT NULL,
	[MaNXB] [int] NOT NULL,
	[MoTa] [nvarchar](1000) NULL,
	[HinhAnh] [nvarchar](255) NULL,
	[GiaNhap] [decimal](18, 0) NULL,
	[GiaBan] [decimal](18, 0) NULL,
	[SoLuongTon] [int] NULL,
	[SoNgayDoiTra] [int] NOT NULL,
	[TrangThai] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sach_NCC]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach_NCC](
	[MaSach] [int] NOT NULL,
	[MaNCC] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC,
	[MaNCC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sach_TacGia]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach_TacGia](
	[MaSach] [int] NOT NULL,
	[MaTacGia] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC,
	[MaTacGia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sach_TheLoai]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach_TheLoai](
	[MaSach] [int] NOT NULL,
	[MaLoai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC,
	[MaLoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TacGia]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TacGia](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaTG]  AS ('TG'+right('000'+CONVERT([varchar](10),[MaID]),(3))) PERSISTED,
	[TenTacGia] [nvarchar](100) NOT NULL,
	[NgaySinh] [date] NULL,
	[QuocTich] [nvarchar](100) NULL,
	[HinhAnh] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoanKhachHang]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoanKhachHang](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaTKKH]  AS (isnull('TKKH'+right('000000'+CONVERT([varchar](10),[MaID]),(6)),'')) PERSISTED NOT NULL,
	[TenDangNhap] [varchar](50) NOT NULL,
	[MatKhau] [varchar](255) NOT NULL,
	[TrangThai] [varchar](20) NULL,
	[NgayTao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoanNhanVien]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoanNhanVien](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaTKNV]  AS (isnull('TKNV'+right('0000'+CONVERT([varchar](10),[MaID]),(4)),'')) PERSISTED NOT NULL,
	[MaQuyen] [int] NOT NULL,
	[TenDangNhap] [varchar](50) NOT NULL,
	[MatKhau] [varchar](255) NOT NULL,
	[TrangThai] [varchar](20) NULL,
	[NgayTao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TheLoai]    Script Date: 3/15/2026 11:15:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TheLoai](
	[MaID] [int] IDENTITY(1,1) NOT NULL,
	[MaLoai]  AS ('TL'+right('000'+CONVERT([varchar](10),[MaID]),(3))) PERSISTED,
	[TenLoai] [nvarchar](100) NOT NULL,
	[MaDanhMuc] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[ChiTietDonHang] ON 
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (1, 2, 1, 1, CAST(150000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:08:00.803' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (2, 3, 2, 1, CAST(250000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:08:00.810' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (3, 4, 1, 2, CAST(100000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:59:00.943' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (4, 5, 2, 1, CAST(150000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:59:00.947' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (5, 6, 21, 1, CAST(350000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:59:00.947' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (6, 7, 1, 5, CAST(100000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:59:00.950' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (7, 8, 2, 1, CAST(120000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:59:00.950' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (8, 9, 21, 1, CAST(80000 AS Decimal(18, 0)), CAST(N'2026-03-18T03:59:00.950' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (9, 10, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-18T05:11:58.713' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (10, 12, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-18T06:16:29.493' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (11, 12, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-18T06:16:29.497' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (12, 12, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-18T06:16:29.500' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (13, 13, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-18T06:24:02.223' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (14, 13, 7, 2, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-18T06:24:02.223' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (15, 14, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-18T07:23:08.380' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (16, 14, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-18T07:23:08.380' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (17, 15, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-18T07:29:11.520' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (18, 15, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-18T07:29:11.523' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (19, 16, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-18T13:55:34.013' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (20, 17, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-18T14:04:47.800' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (21, 18, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-19T16:46:24.677' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (22, 19, 2, 2, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:05:17.220' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (23, 20, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:18:40.063' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (24, 21, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:25:43.933' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (25, 21, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:25:43.937' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (26, 21, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:25:43.937' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (27, 21, 8, 1, CAST(95000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:25:43.937' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (28, 21, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:25:43.937' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (29, 21, 5, 1, CAST(165000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:25:43.937' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (30, 22, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:27:19.640' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (31, 23, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:27:32.550' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (32, 24, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:35:30.300' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (33, 25, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:36:25.037' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (34, 25, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:36:25.037' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (35, 25, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:36:25.037' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (36, 25, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:36:25.037' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (37, 25, 7, 1, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:36:25.040' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (38, 25, 8, 1, CAST(95000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:36:25.040' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (39, 25, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:36:25.040' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (40, 26, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:45:06.527' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (41, 26, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:45:06.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (42, 26, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:45:06.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (43, 26, 8, 1, CAST(95000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:45:06.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (44, 26, 7, 1, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:45:06.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (45, 26, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:45:06.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (46, 26, 5, 1, CAST(165000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:45:06.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (47, 27, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.560' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (48, 27, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.563' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (49, 27, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.563' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (50, 27, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.563' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (51, 27, 8, 1, CAST(95000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.570' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (52, 27, 7, 1, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.570' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (53, 27, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.570' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (54, 27, 5, 1, CAST(165000 AS Decimal(18, 0)), CAST(N'2026-03-19T17:53:28.570' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (55, 28, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-19T18:42:36.000' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (56, 28, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-19T18:42:36.000' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (57, 28, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-19T18:42:36.003' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (58, 29, 7, 1, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-20T04:02:45.917' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (59, 30, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-20T05:21:46.733' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (60, 30, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T05:21:46.740' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (61, 30, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-20T05:21:46.743' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (62, 30, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-20T05:21:46.743' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (63, 31, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-20T13:20:11.027' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (64, 31, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T13:20:11.030' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (65, 31, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-20T13:20:11.030' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (66, 31, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-20T13:20:11.030' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (67, 32, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:34:41.037' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (68, 32, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:34:41.040' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (69, 33, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:38:58.450' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (70, 33, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:38:58.450' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (71, 33, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:38:58.453' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (72, 33, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:38:58.453' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (73, 33, 8, 1, CAST(95000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:38:58.453' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (74, 33, 7, 1, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:38:58.453' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (75, 33, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:38:58.453' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (76, 34, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:50:47.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (77, 34, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:50:47.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (78, 34, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-20T14:50:47.530' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (79, 35, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:01:42.330' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (80, 35, 4, 1, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:01:42.330' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (81, 35, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:01:42.333' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (82, 36, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:12:29.850' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (83, 36, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:12:29.853' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (84, 36, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:12:29.857' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (85, 37, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:25:39.680' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (86, 37, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:25:39.683' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (87, 38, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:26:30.563' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (88, 38, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:26:30.567' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (89, 38, 7, 1, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-20T15:26:30.567' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (90, 39, 3, 2, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-21T17:15:02.283' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (91, 39, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-21T17:15:02.290' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (92, 39, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-21T17:15:02.290' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (93, 40, 1, 2, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-21T17:23:40.210' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (94, 40, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-21T17:23:40.213' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (95, 40, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-21T17:23:40.213' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (96, 40, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-21T17:23:40.213' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (97, 41, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-22T10:16:13.737' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (98, 41, 3, 3, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-22T10:16:13.740' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (99, 42, 2, 1, CAST(86000 AS Decimal(18, 0)), CAST(N'2026-03-22T11:30:50.783' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (100, 42, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-22T11:30:50.783' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (101, 43, 5, 1, CAST(165000 AS Decimal(18, 0)), CAST(N'2026-03-22T23:03:08.250' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (102, 43, 6, 1, CAST(20000 AS Decimal(18, 0)), CAST(N'2026-03-22T23:03:08.253' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (103, 43, 7, 1, CAST(105000 AS Decimal(18, 0)), CAST(N'2026-03-22T23:03:08.257' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (104, 43, 3, 1, CAST(79000 AS Decimal(18, 0)), CAST(N'2026-03-22T23:03:08.257' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (105, 43, 4, 2, CAST(450000 AS Decimal(18, 0)), CAST(N'2026-03-22T23:03:08.257' AS DateTime))
GO
INSERT [dbo].[ChiTietDonHang] ([MaCTDH], [MaDH], [MaSach], [SoLuong], [DonGia], [HanDoiTra]) VALUES (106, 43, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(N'2026-03-22T23:03:08.257' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[ChiTietDonHang] OFF
GO
SET IDENTITY_INSERT [dbo].[ChucVu] ON 
GO
INSERT [dbo].[ChucVu] ([MaID], [TenChucVu], [MoTa]) VALUES (1, N'Quản trị', NULL)
GO
INSERT [dbo].[ChucVu] ([MaID], [TenChucVu], [MoTa]) VALUES (2, N'Nhân viên bán hàng', N'Phụ trách bán hàng tại quầy và online')
GO
INSERT [dbo].[ChucVu] ([MaID], [TenChucVu], [MoTa]) VALUES (3, N'Nhân viên nhập hàng', N'Phụ trách mua sách từ Nhà cung cấp')
GO
INSERT [dbo].[ChucVu] ([MaID], [TenChucVu], [MoTa]) VALUES (4, N'Thủ kho', N'Quản lý xuất nhập và tồn kho')
GO
SET IDENTITY_INSERT [dbo].[ChucVu] OFF
GO
SET IDENTITY_INSERT [dbo].[DanhMuc] ON 
GO
INSERT [dbo].[DanhMuc] ([MaID], [TenDanhMuc]) VALUES (1, N'Văn học')
GO
INSERT [dbo].[DanhMuc] ([MaID], [TenDanhMuc]) VALUES (2, N'Kinh tế')
GO
INSERT [dbo].[DanhMuc] ([MaID], [TenDanhMuc]) VALUES (3, N'Sách Thiếu Nhi')
GO
INSERT [dbo].[DanhMuc] ([MaID], [TenDanhMuc]) VALUES (4, N'Kỹ Năng & Tâm Lý')
GO
INSERT [dbo].[DanhMuc] ([MaID], [TenDanhMuc]) VALUES (5, N'Giáo Dục & Tham Khảo')
GO
INSERT [dbo].[DanhMuc] ([MaID], [TenDanhMuc]) VALUES (6, N'Ngoại Ngữ')
GO
SET IDENTITY_INSERT [dbo].[DanhMuc] OFF
GO
SET IDENTITY_INSERT [dbo].[DonHang] ON 
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (2, NULL, NULL, 1, CAST(150000 AS Decimal(18, 0)), CAST(15000 AS Decimal(18, 0)), CAST(135000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'TaiQuay', N'HoanThanh', CAST(N'2026-03-11T03:08:00.773' AS DateTime), N'Khách vãng lai', NULL, NULL, N'Giao thành công')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (3, NULL, NULL, 2, CAST(250000 AS Decimal(18, 0)), CAST(50000 AS Decimal(18, 0)), CAST(230000 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)), N'Online', N'HoanThanh', CAST(N'2026-03-11T03:08:00.810' AS DateTime), N'Nguyễn Văn Hasan', N'0912345678', N'123 Đường Số 1, Quận 1, TP.HCM', N'Giao thành công')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (4, NULL, NULL, 8, CAST(200000 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)), CAST(170000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'TaiQuay', N'HoanThanh', CAST(N'2026-03-06T03:59:00.937' AS DateTime), N'Khách vãng lai', NULL, NULL, N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (5, NULL, NULL, 7, CAST(150000 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)), CAST(140000 AS Decimal(18, 0)), CAST(20000 AS Decimal(18, 0)), N'Online', N'HoanThanh', CAST(N'2026-03-08T03:59:00.947' AS DateTime), N'Trần Thị Lệ', N'0987654321', N'45 Lê Lợi, Q1, HCM', N'Giao thành công')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (6, NULL, NULL, NULL, CAST(350000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(350000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'TaiQuay', N'HoanThanh', CAST(N'2026-03-10T03:59:00.947' AS DateTime), N'Lê Văn Sang', NULL, NULL, N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (7, NULL, NULL, NULL, CAST(500000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(530000 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-11T03:59:00.947' AS DateTime), N'Phạm Tiến Đạt', N'0901112233', N'Quận 7, HCM', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (8, NULL, NULL, NULL, CAST(120000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(120000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'TaiQuay', N'DaHuy', CAST(N'2026-03-01T03:59:00.950' AS DateTime), N'Khách vãng lai', NULL, NULL, N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (9, NULL, NULL, NULL, CAST(80000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(105000 AS Decimal(18, 0)), CAST(25000 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-09T03:59:00.950' AS DateTime), N'Ngô Gia Bảo', N'0999888777', N'Quận Tân Bình, HCM', N'Giao thất bại')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (10, 13, 1, 1, CAST(110000 AS Decimal(18, 0)), CAST(11000 AS Decimal(18, 0)), CAST(99000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'TaiQuay', N'HoanThanh', CAST(N'2026-03-11T05:11:58.417' AS DateTime), NULL, NULL, NULL, N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (12, NULL, 7, NULL, CAST(275000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(275000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-11T06:16:29.477' AS DateTime), N'Hasan', N'0123456789', N'123, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (13, NULL, 7, NULL, CAST(230000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(230000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaXacNhan', CAST(N'2026-03-11T06:24:02.207' AS DateTime), N'Hasan', N'0123456789', N'13324, Phường Sài Gòn, TP Hồ Chí Minh', N'Đang giao hàng')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (14, NULL, 9, NULL, CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-11T07:23:08.350' AS DateTime), N'TUYETVY', N'9876543210', N'háđbhád, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (15, NULL, 9, NULL, CAST(529000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(529000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-11T07:29:11.500' AS DateTime), N'TUYETVY', N'9876543210', N'312 Hoà Bình, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (16, NULL, 7, NULL, CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-11T13:55:33.987' AS DateTime), N'Hasan', N'0123456789', N'312 Hòa Bình, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (17, NULL, 7, NULL, CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-11T14:04:47.773' AS DateTime), N'Hasan', N'0123456789', N'312 Hòa Bình, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (18, NULL, 7, NULL, CAST(86000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(86000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T16:46:24.653' AS DateTime), N'Hasan', N'0123456789', N'321 Hòa Bình, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (19, NULL, 7, NULL, CAST(172000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(172000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T17:05:17.210' AS DateTime), N'Hasan', N'0123456789', N'312 Hòa Bình, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (20, NULL, 7, NULL, CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T17:18:40.050' AS DateTime), N'Hasan', N'0123456789', N'ádsđa, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (21, NULL, 7, NULL, CAST(895000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(895000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T17:25:43.917' AS DateTime), N'Hasan', N'0123456789', N'31245678, Phường Bàn Cờ, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (22, NULL, 7, NULL, CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T17:27:19.627' AS DateTime), N'Hasan', N'0123456789', N'ádád, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (23, NULL, 7, NULL, CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T17:27:32.537' AS DateTime), N'Hasan', N'0123456789', N'qeqw, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (24, NULL, 7, NULL, CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-12T17:35:30.280' AS DateTime), N'Hasan', N'0123456789', N'12, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (25, NULL, 7, NULL, CAST(945000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(945000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-12T17:36:25.020' AS DateTime), N'Hasan', N'0123456789', N'223456, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (26, NULL, 7, NULL, CAST(1000000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(1000000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T17:45:06.510' AS DateTime), N'Hasan', N'0123456789', N'12345, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (27, NULL, 7, NULL, CAST(1110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(1110000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-12T17:53:28.547' AS DateTime), N'Hasan', N'0123456789', N'12345, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (28, NULL, 7, NULL, CAST(615000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(615000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-12T18:42:35.980' AS DateTime), N'Hasan', N'0123456789', N'1234, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (29, NULL, 7, NULL, CAST(105000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(105000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-13T04:02:45.880' AS DateTime), N'Hasan', N'0123456789', N'0123456, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (30, NULL, 7, NULL, CAST(725000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(725000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-13T05:21:46.713' AS DateTime), N'Hasan', N'0123456789', N'234trrrgf, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (31, NULL, 7, NULL, CAST(725000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(725000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-13T13:20:11.000' AS DateTime), N'Hasan', N'0123456789', N'fshdfhd, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (32, NULL, 7, NULL, CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-13T14:34:41.007' AS DateTime), N'Hasan', N'0123456789', N'312 Hòa Bình, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (33, NULL, 7, NULL, CAST(945000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(945000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-13T14:38:58.430' AS DateTime), N'Hasan', N'0123456789', N'jhágd, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (34, NULL, 7, NULL, CAST(275000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(275000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-13T14:50:47.513' AS DateTime), N'Hasan', N'0123456789', N'íhd, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (35, NULL, 7, NULL, CAST(615000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(615000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-13T15:01:42.313' AS DateTime), N'Hasan', N'0123456789', N'hsfdhjjsdfhjbsfdhj, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (36, NULL, 7, NULL, CAST(275000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(275000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-13T15:12:29.830' AS DateTime), N'Hasan', N'0123456789', N'ad, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (37, NULL, 7, NULL, CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-13T15:25:39.660' AS DateTime), N'Hasan', N'0123456789', N'ád, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (38, NULL, 7, NULL, CAST(204000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(204000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-13T15:26:30.540' AS DateTime), N'Hasan', N'0123456789', N'ad, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (39, NULL, 7, NULL, CAST(354000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(354000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-14T17:15:02.253' AS DateTime), N'Hasan', N'0123456789', N'qweqweq, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (40, NULL, 7, NULL, CAST(405000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(405000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-14T17:23:40.187' AS DateTime), N'Hasan', N'0123456789', N'gfhg, Phường Sài Gòn, TP Hồ Chí Minh', N'Giao thất bại')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (41, NULL, 7, NULL, CAST(347000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(347000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'DaHuy', CAST(N'2026-03-15T10:16:13.690' AS DateTime), N'Hasan', N'0123456789', N'312 Hòa Bình, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (42, NULL, 7, NULL, CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(196000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'HoanThanh', CAST(N'2026-03-15T11:30:50.763' AS DateTime), N'Hasan', N'0123456789', N'ađá, Phường Sài Gòn, TP Hồ Chí Minh', N'Giao thành công')
GO
INSERT [dbo].[DonHang] ([MaID], [MaNV], [MaKH], [MaKM], [TongTien], [TienGiam], [ThanhTien], [PhiGiaoHang], [LoaiDonHang], [TrangThaiDon], [NgayTao], [TenNguoiNhan], [SDTNhan], [DiaChiGiao], [TrangThaiGiaoHang]) VALUES (43, NULL, 7, NULL, CAST(1379000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(1379000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), N'Online', N'ChoXuLy', CAST(N'2026-03-15T23:03:08.227' AS DateTime), N'Hasan', N'0123456789', N'Hẻm 98/14, Phường Sài Gòn, TP Hồ Chí Minh', N'Chờ chuẩn bị')
GO
SET IDENTITY_INSERT [dbo].[DonHang] OFF
GO
SET IDENTITY_INSERT [dbo].[HoaDon] ON 
GO
INSERT [dbo].[HoaDon] ([MaID], [MaDH], [HinhThucThanhToan], [SoTienKhachTra], [TienThua], [NgayThanhToan]) VALUES (1, 2, N'Tiền mặt', CAST(150000 AS Decimal(18, 0)), CAST(15000 AS Decimal(18, 0)), CAST(N'2026-03-11T03:08:00.807' AS DateTime))
GO
INSERT [dbo].[HoaDon] ([MaID], [MaDH], [HinhThucThanhToan], [SoTienKhachTra], [TienThua], [NgayThanhToan]) VALUES (2, 3, N'Chuyển khoản', CAST(230000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(N'2026-03-11T03:08:00.810' AS DateTime))
GO
INSERT [dbo].[HoaDon] ([MaID], [MaDH], [HinhThucThanhToan], [SoTienKhachTra], [TienThua], [NgayThanhToan]) VALUES (3, 4, N'Tiền mặt', CAST(200000 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)), CAST(N'2026-03-06T03:59:00.943' AS DateTime))
GO
INSERT [dbo].[HoaDon] ([MaID], [MaDH], [HinhThucThanhToan], [SoTienKhachTra], [TienThua], [NgayThanhToan]) VALUES (4, 5, N'Chuyển khoản', CAST(140000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(N'2026-03-08T03:59:00.947' AS DateTime))
GO
INSERT [dbo].[HoaDon] ([MaID], [MaDH], [HinhThucThanhToan], [SoTienKhachTra], [TienThua], [NgayThanhToan]) VALUES (5, 6, N'Quẹt thẻ (Ví MoMo)', CAST(350000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(N'2026-03-10T03:59:00.947' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[HoaDon] OFF
GO
SET IDENTITY_INSERT [dbo].[KhachHang] ON 
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (1, 1, N'Nguyễn Văn An', N'0901111111', N'nguyenvanan@gmail.com', N'123 Lê Lợi, Q.1, TP.HCM', 150, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (2, 2, N'Trần Thị Bích', N'0902222222', N'tranbich_2k@gmail.com', N'456 Nguyễn Trãi, Q.5, TP.HCM', 320, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (3, 3, N'Lê Hoàng Nam', N'0903333333', N'namle123@gmail.com', N'789 Cách Mạng Tháng 8, Q.10, TP.HCM', 0, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (4, NULL, N'Phạm Thu Thảo', N'0904444444', N'thuthaopham@gmail.com', N'321 Điện Biên Phủ, Tân Bình, TP.HCM', 50, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (5, NULL, N'Vũ Ngọc Đăng', N'0905555555', N'dangvu_vip@gmail.com', N'654 Võ Văn Kiệt, Bình Thạnh, TP.HCM', 1200, N'NgungHoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (6, NULL, N'Trần Nguyễn Tuyết Vy', N'0123456785', N'tuyetvy', N'312 Hòa Bình', 0, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (7, 8, N'Hasan', N'0123456789', N'hasan@gmail.com', N'Hẻm 98/14, Phường Sài Gòn, TP Hồ Chí Minh', 12930, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (8, 9, N'hasan', N'0901234567', N'add@gmail.com', N'Chưa cập nhật', 0, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (9, 10, N'TUYETVY', N'9876543210', N'tvy@gmail.com', N'312 Hoà Bình, Phường Sài Gòn, TP Hồ Chí Minh', 725, N'HoatDong')
GO
INSERT [dbo].[KhachHang] ([MaID], [MaTaiKhoanKH], [HoTen], [SoDienThoai], [Email], [DiaChi], [DiemTichLuy], [TrangThai]) VALUES (10, 11, N'dađá', N'0989846511', N'áđá@gmail.com', N'Chưa cập nhật', 0, N'HoatDong')
GO
SET IDENTITY_INSERT [dbo].[KhachHang] OFF
GO
SET IDENTITY_INSERT [dbo].[KhuyenMai] ON 
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (1, N'Khuyến mãi Khai Trương (Giảm 10%)', CAST(10.00 AS Decimal(5, 2)), CAST(0 AS Decimal(18, 0)), CAST(100000 AS Decimal(18, 0)), CAST(N'2025-01-01' AS Date), CAST(N'2026-12-31' AS Date), N'HoatDong')
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (2, N'Siêu Sale Mùa Hè (Giảm 50k)', CAST(0.00 AS Decimal(5, 2)), CAST(50000 AS Decimal(18, 0)), CAST(200000 AS Decimal(18, 0)), CAST(N'2026-03-01' AS Date), CAST(N'2026-06-30' AS Date), N'HoatDong')
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (3, N'Black Friday 2024 (Đã hết hạn)', CAST(20.00 AS Decimal(5, 2)), CAST(0 AS Decimal(18, 0)), CAST(500000 AS Decimal(18, 0)), CAST(N'2024-11-20' AS Date), CAST(N'2024-11-30' AS Date), N'HetHan')
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (4, N'Chào Hè Sôi Động 2026 (Giảm 15%)', CAST(15.00 AS Decimal(5, 2)), CAST(0 AS Decimal(18, 0)), CAST(150000 AS Decimal(18, 0)), CAST(N'2026-05-01' AS Date), CAST(N'2026-08-31' AS Date), N'HoatDong')
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (5, N'Flash Sale Cuối Tuần (Trừ 30k)', CAST(0.00 AS Decimal(5, 2)), CAST(30000 AS Decimal(18, 0)), CAST(100000 AS Decimal(18, 0)), CAST(N'2026-03-01' AS Date), CAST(N'2026-12-31' AS Date), N'HoatDong')
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (6, N'Mừng Tựu Trường (Giảm 20%)', CAST(20.00 AS Decimal(5, 2)), CAST(0 AS Decimal(18, 0)), CAST(300000 AS Decimal(18, 0)), CAST(N'2026-08-15' AS Date), CAST(N'2026-09-15' AS Date), N'HoatDong')
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (7, N'Giảm Giá Xả Kho (Trừ 100k)', CAST(0.00 AS Decimal(5, 2)), CAST(100000 AS Decimal(18, 0)), CAST(500000 AS Decimal(18, 0)), CAST(N'2026-01-01' AS Date), CAST(N'2026-12-31' AS Date), N'HoatDong')
GO
INSERT [dbo].[KhuyenMai] ([MaID], [TenKM], [PhanTramGiam], [SoTienGiam], [DonHangToiThieu], [NgayBatDau], [NgayKetThuc], [TrangThai]) VALUES (8, N'Lễ Tình Nhân Valentine (Đã kết thúc)', CAST(10.00 AS Decimal(5, 2)), CAST(0 AS Decimal(18, 0)), CAST(200000 AS Decimal(18, 0)), CAST(N'2026-02-10' AS Date), CAST(N'2026-02-15' AS Date), N'HetHan')
GO
SET IDENTITY_INSERT [dbo].[KhuyenMai] OFF
GO
SET IDENTITY_INSERT [dbo].[LichSuKho] ON 
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (1, 1, N'NHAP_HANG', 1001, 50, 0, NULL, CAST(N'2026-03-11T02:06:48.670' AS DateTime), N'Nhập hàng khai trương')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (2, 2, N'NHAP_HANG', 1001, 100, 0, NULL, CAST(N'2026-03-11T02:06:48.670' AS DateTime), N'Nhập hàng khai trương')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (3, 3, N'NHAP_HANG', 1002, 80, 0, NULL, CAST(N'2026-03-11T02:06:48.670' AS DateTime), N'Nhập hàng từ Nhã Nam')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (4, 4, N'NHAP_HANG', 2024, 30, 0, NULL, CAST(N'2026-03-11T02:06:48.677' AS DateTime), N'Nhập hàng định kỳ')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (5, 5, N'NHAP_HANG', 2024, 45, 0, NULL, CAST(N'2026-03-11T02:06:48.677' AS DateTime), N'Nhập hàng định kỳ')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (6, 6, N'NHAP_HANG', 2024, 200, 0, NULL, CAST(N'2026-03-11T02:06:48.677' AS DateTime), N'Nhập hàng định kỳ')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (7, 7, N'NHAP_HANG', 2024, 60, 0, NULL, CAST(N'2026-03-11T02:06:48.677' AS DateTime), N'Nhập hàng định kỳ')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (8, 8, N'NHAP_HANG', 2024, 120, 0, NULL, CAST(N'2026-03-11T02:06:48.677' AS DateTime), N'Nhập hàng định kỳ')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (9, 9, N'NHAP_HANG', 2024, 50, 0, NULL, CAST(N'2026-03-11T02:06:48.677' AS DateTime), N'Nhập hàng định kỳ')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (10, 10, N'NHAP_HANG', 2026, 40, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (11, 11, N'NHAP_HANG', 2026, 70, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (12, 12, N'NHAP_HANG', 2026, 25, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (13, 13, N'NHAP_HANG', 2026, 35, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (14, 14, N'NHAP_HANG', 2026, 500, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (15, 15, N'NHAP_HANG', 2026, 100, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (16, 16, N'NHAP_HANG', 2026, 45, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (17, 17, N'NHAP_HANG', 2026, 30, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (18, 18, N'NHAP_HANG', 2026, 20, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (19, 19, N'NHAP_HANG', 2026, 85, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (20, 20, N'NHAP_HANG', 2026, 50, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (21, 21, N'NHAP_HANG', 2026, 150, 0, NULL, CAST(N'2026-03-11T02:06:48.687' AS DateTime), N'Nhập hàng bổ sung danh mục')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (22, 1, N'BAN_HANG', 10, -1, 0, NULL, CAST(N'2026-03-11T05:11:59.277' AS DateTime), N'Bán hàng theo hóa đơn HD010')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (23, 1, N'BAN_HANG', 12, -1, 0, NULL, CAST(N'2026-03-11T06:16:29.513' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (24, 2, N'BAN_HANG', 12, -1, 0, NULL, CAST(N'2026-03-11T06:16:29.513' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (25, 3, N'BAN_HANG', 12, -1, 0, NULL, CAST(N'2026-03-11T06:16:29.513' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (26, 6, N'BAN_HANG', 13, -1, 0, NULL, CAST(N'2026-03-11T06:24:02.237' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (27, 7, N'BAN_HANG', 13, -2, 0, NULL, CAST(N'2026-03-11T06:24:02.240' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (28, 1, N'BAN_HANG', 14, -1, 0, NULL, CAST(N'2026-03-11T07:23:08.407' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (29, 2, N'BAN_HANG', 14, -1, 0, NULL, CAST(N'2026-03-11T07:23:08.410' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (30, 3, N'BAN_HANG', 15, -1, 0, NULL, CAST(N'2026-03-11T07:29:11.540' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (31, 4, N'BAN_HANG', 15, -1, 0, NULL, CAST(N'2026-03-11T07:29:11.540' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (32, 1, N'BAN_HANG', 16, -1, 0, NULL, CAST(N'2026-03-11T13:55:34.033' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (33, 1, N'BAN_HANG', 17, -1, 0, NULL, CAST(N'2026-03-11T14:04:47.823' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (34, 2, N'BAN_HANG', 18, -1, 0, NULL, CAST(N'2026-03-12T16:46:24.690' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (35, 2, N'BAN_HANG', 19, -2, 0, NULL, CAST(N'2026-03-12T17:05:17.233' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (36, 1, N'BAN_HANG', 20, -1, 0, NULL, CAST(N'2026-03-12T17:18:40.073' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (37, 2, N'BAN_HANG', 21, -1, 0, NULL, CAST(N'2026-03-12T17:25:43.950' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (38, 3, N'BAN_HANG', 21, -1, 0, NULL, CAST(N'2026-03-12T17:25:43.950' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (39, 4, N'BAN_HANG', 21, -1, 0, NULL, CAST(N'2026-03-12T17:25:43.950' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (40, 8, N'BAN_HANG', 21, -1, 0, NULL, CAST(N'2026-03-12T17:25:43.950' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (41, 6, N'BAN_HANG', 21, -1, 0, NULL, CAST(N'2026-03-12T17:25:43.950' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (42, 5, N'BAN_HANG', 21, -1, 0, NULL, CAST(N'2026-03-12T17:25:43.950' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (43, 1, N'BAN_HANG', 22, -1, 0, NULL, CAST(N'2026-03-12T17:27:19.650' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (44, 1, N'BAN_HANG', 23, -1, 0, NULL, CAST(N'2026-03-12T17:27:32.563' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (45, 1, N'BAN_HANG', 24, -1, 0, NULL, CAST(N'2026-03-12T17:35:30.320' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (46, 3, N'BAN_HANG', 25, -1, 0, NULL, CAST(N'2026-03-12T17:36:25.050' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (47, 2, N'BAN_HANG', 25, -1, 0, NULL, CAST(N'2026-03-12T17:36:25.050' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (48, 1, N'BAN_HANG', 25, -1, 0, NULL, CAST(N'2026-03-12T17:36:25.050' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (49, 4, N'BAN_HANG', 25, -1, 0, NULL, CAST(N'2026-03-12T17:36:25.050' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (50, 7, N'BAN_HANG', 25, -1, 0, NULL, CAST(N'2026-03-12T17:36:25.050' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (51, 8, N'BAN_HANG', 25, -1, 0, NULL, CAST(N'2026-03-12T17:36:25.050' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (52, 6, N'BAN_HANG', 25, -1, 0, NULL, CAST(N'2026-03-12T17:36:25.050' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (53, 2, N'BAN_HANG', 26, -1, 0, NULL, CAST(N'2026-03-12T17:45:06.543' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (54, 3, N'BAN_HANG', 26, -1, 0, NULL, CAST(N'2026-03-12T17:45:06.543' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (55, 4, N'BAN_HANG', 26, -1, 0, NULL, CAST(N'2026-03-12T17:45:06.543' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (56, 8, N'BAN_HANG', 26, -1, 0, NULL, CAST(N'2026-03-12T17:45:06.543' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (57, 7, N'BAN_HANG', 26, -1, 0, NULL, CAST(N'2026-03-12T17:45:06.543' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (58, 6, N'BAN_HANG', 26, -1, 0, NULL, CAST(N'2026-03-12T17:45:06.543' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (59, 5, N'BAN_HANG', 26, -1, 0, NULL, CAST(N'2026-03-12T17:45:06.543' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (60, 1, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.587' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (61, 2, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.587' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (62, 3, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.587' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (63, 4, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.587' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (64, 8, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.590' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (65, 7, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.590' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (66, 6, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.590' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (67, 5, N'BAN_HANG', 27, -1, 0, NULL, CAST(N'2026-03-12T17:53:28.590' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (68, 2, N'BAN_HANG', 28, -1, 0, NULL, CAST(N'2026-03-12T18:42:36.013' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (69, 3, N'BAN_HANG', 28, -1, 0, NULL, CAST(N'2026-03-12T18:42:36.017' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (70, 4, N'BAN_HANG', 28, -1, 0, NULL, CAST(N'2026-03-12T18:42:36.017' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (71, 7, N'BAN_HANG', 29, -1, 0, NULL, CAST(N'2026-03-13T04:02:45.943' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (72, 1, N'BAN_HANG', 30, -1, 0, NULL, CAST(N'2026-03-13T05:21:46.760' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (73, 2, N'BAN_HANG', 30, -1, 0, NULL, CAST(N'2026-03-13T05:21:46.760' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (74, 3, N'BAN_HANG', 30, -1, 0, NULL, CAST(N'2026-03-13T05:21:46.760' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (75, 4, N'BAN_HANG', 30, -1, 0, NULL, CAST(N'2026-03-13T05:21:46.760' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (76, 1, N'BAN_HANG', 31, -1, 0, NULL, CAST(N'2026-03-13T13:20:11.057' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (77, 2, N'BAN_HANG', 31, -1, 0, NULL, CAST(N'2026-03-13T13:20:11.057' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (78, 3, N'BAN_HANG', 31, -1, 0, NULL, CAST(N'2026-03-13T13:20:11.060' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (79, 4, N'BAN_HANG', 31, -1, 0, NULL, CAST(N'2026-03-13T13:20:11.060' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (80, 1, N'BAN_HANG', 32, -1, 0, NULL, CAST(N'2026-03-13T14:34:41.063' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (81, 2, N'BAN_HANG', 32, -1, 0, NULL, CAST(N'2026-03-13T14:34:41.063' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (82, 1, N'BAN_HANG', 33, -1, 0, NULL, CAST(N'2026-03-13T14:38:58.483' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (83, 2, N'BAN_HANG', 33, -1, 0, NULL, CAST(N'2026-03-13T14:38:58.487' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (84, 3, N'BAN_HANG', 33, -1, 0, NULL, CAST(N'2026-03-13T14:38:58.490' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (85, 4, N'BAN_HANG', 33, -1, 0, NULL, CAST(N'2026-03-13T14:38:58.490' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (86, 8, N'BAN_HANG', 33, -1, 0, NULL, CAST(N'2026-03-13T14:38:58.490' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (87, 7, N'BAN_HANG', 33, -1, 0, NULL, CAST(N'2026-03-13T14:38:58.490' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (88, 6, N'BAN_HANG', 33, -1, 0, NULL, CAST(N'2026-03-13T14:38:58.490' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (89, 1, N'BAN_HANG', 34, -1, 0, NULL, CAST(N'2026-03-13T14:50:47.547' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (90, 2, N'BAN_HANG', 34, -1, 0, NULL, CAST(N'2026-03-13T14:50:47.547' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (91, 3, N'BAN_HANG', 34, -1, 0, NULL, CAST(N'2026-03-13T14:50:47.547' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (92, 3, N'BAN_HANG', 35, -1, 0, NULL, CAST(N'2026-03-13T15:01:42.350' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (93, 4, N'BAN_HANG', 35, -1, 0, NULL, CAST(N'2026-03-13T15:01:42.350' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (94, 2, N'BAN_HANG', 35, -1, 0, NULL, CAST(N'2026-03-13T15:01:42.350' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (95, 1, N'BAN_HANG', 36, -1, 0, NULL, CAST(N'2026-03-13T15:12:29.870' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (96, 2, N'BAN_HANG', 36, -1, 0, NULL, CAST(N'2026-03-13T15:12:29.873' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (97, 3, N'BAN_HANG', 36, -1, 0, NULL, CAST(N'2026-03-13T15:12:29.873' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (98, 1, N'BAN_HANG', 37, -1, 0, NULL, CAST(N'2026-03-13T15:25:39.697' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (99, 2, N'BAN_HANG', 37, -1, 0, NULL, CAST(N'2026-03-13T15:25:39.700' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (100, 3, N'BAN_HANG', 38, -1, 0, NULL, CAST(N'2026-03-13T15:26:30.587' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (101, 6, N'BAN_HANG', 38, -1, 0, NULL, CAST(N'2026-03-13T15:26:30.587' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (102, 7, N'BAN_HANG', 38, -1, 0, NULL, CAST(N'2026-03-13T15:26:30.587' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (103, 3, N'BAN_HANG', 39, -2, 0, NULL, CAST(N'2026-03-14T17:15:02.310' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (104, 1, N'BAN_HANG', 39, -1, 0, NULL, CAST(N'2026-03-14T17:15:02.313' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (105, 2, N'BAN_HANG', 39, -1, 0, NULL, CAST(N'2026-03-14T17:15:02.313' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (106, 1, N'BAN_HANG', 40, -2, 0, NULL, CAST(N'2026-03-14T17:23:40.230' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (107, 6, N'BAN_HANG', 40, -1, 0, NULL, CAST(N'2026-03-14T17:23:40.230' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (108, 2, N'BAN_HANG', 40, -1, 0, NULL, CAST(N'2026-03-14T17:23:40.233' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (109, 3, N'BAN_HANG', 40, -1, 0, NULL, CAST(N'2026-03-14T17:23:40.233' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (110, 1, N'BAN_HANG', 41, -1, 0, NULL, CAST(N'2026-03-15T10:16:13.760' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (111, 3, N'BAN_HANG', 41, -3, 0, NULL, CAST(N'2026-03-15T10:16:13.760' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (112, 2, N'BAN_HANG', 42, -1, 0, NULL, CAST(N'2026-03-15T11:30:50.800' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (113, 1, N'BAN_HANG', 42, -1, 0, NULL, CAST(N'2026-03-15T11:30:50.800' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (114, 5, N'BAN_HANG', 43, -1, 0, NULL, CAST(N'2026-03-15T23:03:08.267' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (115, 6, N'BAN_HANG', 43, -1, 0, NULL, CAST(N'2026-03-15T23:03:08.270' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (116, 7, N'BAN_HANG', 43, -1, 0, NULL, CAST(N'2026-03-15T23:03:08.270' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (117, 3, N'BAN_HANG', 43, -1, 0, NULL, CAST(N'2026-03-15T23:03:08.270' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (118, 4, N'BAN_HANG', 43, -2, 0, NULL, CAST(N'2026-03-15T23:03:08.270' AS DateTime), N'Bán hàng online')
GO
INSERT [dbo].[LichSuKho] ([MaID], [MaSach], [LoaiGiaoDich], [MaChungTu], [SoLuongThayDoi], [SoLuongLoi], [PhanLoaiTinhTrang], [NgayGioTao], [GhiChu]) VALUES (119, 1, N'BAN_HANG', 43, -1, 0, NULL, CAST(N'2026-03-15T23:03:08.270' AS DateTime), N'Bán hàng online')
GO
SET IDENTITY_INSERT [dbo].[LichSuKho] OFF
GO
SET IDENTITY_INSERT [dbo].[NhaCungCap] ON 
GO
INSERT [dbo].[NhaCungCap] ([MaID], [TenNCC], [SoDienThoai], [DiaChi], [TrangThai]) VALUES (1, N'Công ty CP Phát hành sách TP.HCM - FAHASA', N'1900636467', N'60-62 Lê Lợi, Quận 1, TP.HCM', N'HoatDong')
GO
INSERT [dbo].[NhaCungCap] ([MaID], [TenNCC], [SoDienThoai], [DiaChi], [TrangThai]) VALUES (2, N'Công ty Văn Hóa Nhã Nam', N'02437677524', N'59 Đỗ Quang, Cầu Giấy, Hà Nội', N'HoatDong')
GO
INSERT [dbo].[NhaCungCap] ([MaID], [TenNCC], [SoDienThoai], [DiaChi], [TrangThai]) VALUES (3, N'Fahasa', N'0281234567', N'TP.HCM', N'HoatDong')
GO
SET IDENTITY_INSERT [dbo].[NhaCungCap] OFF
GO
SET IDENTITY_INSERT [dbo].[NhanVien] ON 
GO
INSERT [dbo].[NhanVien] ([MaID], [MaChucVu], [MaTaiKhoanNV], [HoTen], [Email], [SoDienThoai]) VALUES (6, 2, 5, N'Latif', N'hasan@gmail.com', N'0987654321')
GO
INSERT [dbo].[NhanVien] ([MaID], [MaChucVu], [MaTaiKhoanNV], [HoTen], [Email], [SoDienThoai]) VALUES (7, 3, 6, N'Latif Hasan', N'aksjd@gmail.com', N'1234567890')
GO
INSERT [dbo].[NhanVien] ([MaID], [MaChucVu], [MaTaiKhoanNV], [HoTen], [Email], [SoDienThoai]) VALUES (11, 4, 8, N'TDi', N'tvytrannguyen@gmail.com', N'0192384765')
GO
INSERT [dbo].[NhanVien] ([MaID], [MaChucVu], [MaTaiKhoanNV], [HoTen], [Email], [SoDienThoai]) VALUES (13, 1, 1, N'Admin Tối Cao', N'admin@bookstore.com', N'0999999999')
GO
SET IDENTITY_INSERT [dbo].[NhanVien] OFF
GO
SET IDENTITY_INSERT [dbo].[NhaXuatBan] ON 
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (1, N'NXB Trẻ', N'161B Lý Chính Thắng, Quận 3, TP.HCM')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (2, N'NXB Tổng Hợp TP.HCM', N'62 Nguyễn Thị Minh Khai, Quận 1, TP.HCM')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (3, N'NXB Hội Nhà Văn', N'Số 65 Nguyễn Du, Hai Bà Trưng, Hà Nội')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (4, N'NXB Kim Đồng', N'55 Quang Trung, Hai Bà Trưng, Hà Nội')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (5, N'NXB Thế Giới', N'46 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (6, N'NXB Đại Học Cambridge', N'Cambridge, United Kingdom')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (7, N'NXB Giáo Dục', N'81 Trần Hưng Đạo, Hà Nội')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (8, N'NXB Phụ Nữ', N'39 Hàng Chuối, Hà Nội')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (9, N'NXB Lao Động', N'175 Giảng Võ, Ba Đình, Hà Nội')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (10, N'NXB Văn Học', N'290 Tây Sơn, Đống Đa, Hà Nội')
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (11, N'NXB Trẻ', NULL)
GO
INSERT [dbo].[NhaXuatBan] ([MaID], [TenNXB], [DiaChi]) VALUES (12, N'NXB Kim Đồng', NULL)
GO
SET IDENTITY_INSERT [dbo].[NhaXuatBan] OFF
GO
SET IDENTITY_INSERT [dbo].[PhanQuyen] ON 
GO
INSERT [dbo].[PhanQuyen] ([MaID], [TenQuyen], [MoTa], [QlSach], [QlThuocTinh], [QlBanHang], [QlKhuyenMai], [QlHoaDon], [QlPhieuDoiTra], [QlNhapHang], [QlPhieuNhap], [QlKhachHang], [QlNCC], [QlNhanVien], [QlTaiKhoan], [QlPhanQuyen], [QlThongKe]) VALUES (1, N'Quản trị viên', N'Toàn quyền hệ thống', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
GO
INSERT [dbo].[PhanQuyen] ([MaID], [TenQuyen], [MoTa], [QlSach], [QlThuocTinh], [QlBanHang], [QlKhuyenMai], [QlHoaDon], [QlPhieuDoiTra], [QlNhapHang], [QlPhieuNhap], [QlKhachHang], [QlNCC], [QlNhanVien], [QlTaiKhoan], [QlPhanQuyen], [QlThongKe]) VALUES (2, N'Nhân viên bán hàng', N'Phụ trách bán hàng', 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0)
GO
INSERT [dbo].[PhanQuyen] ([MaID], [TenQuyen], [MoTa], [QlSach], [QlThuocTinh], [QlBanHang], [QlKhuyenMai], [QlHoaDon], [QlPhieuDoiTra], [QlNhapHang], [QlPhieuNhap], [QlKhachHang], [QlNCC], [QlNhanVien], [QlTaiKhoan], [QlPhanQuyen], [QlThongKe]) VALUES (3, N'Thủ kho', N'Quản lý tổng quát kho bãi', 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1)
GO
INSERT [dbo].[PhanQuyen] ([MaID], [TenQuyen], [MoTa], [QlSach], [QlThuocTinh], [QlBanHang], [QlKhuyenMai], [QlHoaDon], [QlPhieuDoiTra], [QlNhapHang], [QlPhieuNhap], [QlKhachHang], [QlNCC], [QlNhanVien], [QlTaiKhoan], [QlPhanQuyen], [QlThongKe]) VALUES (4, N'Nhân viên nhập hàng', N'Phụ trách mua hàng từ NCC', 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0)
GO
SET IDENTITY_INSERT [dbo].[PhanQuyen] OFF
GO
SET IDENTITY_INSERT [dbo].[Sach] ON 
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (1, N'Đất Rừng Phương Nam', N'9786041185554', 1, N'Tác phẩm viết về cuộc đời phiêu bạt của cậu bé An tại miền Tây Nam Bộ trong kháng chiến chống Pháp.', N'dat-rung-phuong-nam.jpg', CAST(80000 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), 26, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (2, N'Đắc Nhân Tâm', N'9786045880530', 2, N'Cuốn sách đưa ra các lời khuyên về cách giao tiếp và ứng xử để đạt được thành công trong cuộc sống.', N'dac-nhan-tam.jpg', CAST(60000 AS Decimal(18, 0)), CAST(86000 AS Decimal(18, 0)), 79, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (3, N'Nhà Giả Kim', N'9786041100519', 1, N'Câu chuyện về hành trình của chàng chăn cừu Santiago đi tìm kho báu và định mệnh của mình.', N'nha-gia-kim.jpg', CAST(55000 AS Decimal(18, 0)), CAST(79000 AS Decimal(18, 0)), 59, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (4, N'Marketing Căn Bản', N'9786047744312', 5, N'Cuốn cẩm nang kinh điển về Marketing cung cấp kiến thức nền tảng và chuyên sâu về thị trường.', N'marketing-can-ban.jpg', CAST(350000 AS Decimal(18, 0)), CAST(450000 AS Decimal(18, 0)), 18, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (5, N'Trên Đỉnh Phố Wall', N'9786045631231', 5, N'Kinh nghiệm đầu tư chứng khoán từ một trong những nhà quản lý quỹ thành công nhất thế giới.', N'tren-dinh-pho-wall.jpg', CAST(120000 AS Decimal(18, 0)), CAST(165000 AS Decimal(18, 0)), 41, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (6, N'Doraemon Tuyển Tập Ngắn - Tập 1', N'9786042145678', 4, N'Những cuộc phiêu lưu của chú mèo máy thông minh đến từ tương lai và cậu bé Nobita.', N'doraemon.jpg', CAST(15000 AS Decimal(18, 0)), CAST(20000 AS Decimal(18, 0)), 191, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (7, N'Totto-chan Bên Cửa Sổ', N'9786045643219', 5, N'Cuốn sách giáo dục nổi tiếng về tuổi thơ và phương pháp giáo dục khai phóng.', N'tottochan.jpg', CAST(75000 AS Decimal(18, 0)), CAST(105000 AS Decimal(18, 0)), 51, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (8, N'Trên Đường Băng', N'9786041065431', 1, N'Tập hợp những bài viết truyền cảm hứng cho thanh niên về tư duy làm giàu và học tập.', N'tre-duong-bang.jpg', CAST(65000 AS Decimal(18, 0)), CAST(95000 AS Decimal(18, 0)), 115, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (9, N'English Grammar in Use', N'9781108457651', 6, N'Cuốn sách ngữ pháp tiếng Anh bán chạy nhất thế giới dành cho người học trình độ trung cấp.', N'grammar-in-use.jpg', CAST(250000 AS Decimal(18, 0)), CAST(320000 AS Decimal(18, 0)), 50, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (10, N'Phía Sau Nghi Can X', N'9786045656340', 3, N'Một kiệt tác trinh thám tâm lý của Keigo, xoay quanh cuộc đấu trí giữa một thiên tài toán học và một thiên tài vật lý.', N'phia-sau-nghi-can-x.jpg', CAST(85000 AS Decimal(18, 0)), CAST(115000 AS Decimal(18, 0)), 40, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (11, N'Cha Giàu Cha Nghèo', N'9786043215678', 2, N'Cuốn sách thay đổi tư duy về tài chính cá nhân, giúp bạn hiểu rõ sự khác biệt giữa tài sản và tiêu sản.', N'cha-giau-cha-ngheo.jpg', CAST(140000 AS Decimal(18, 0)), CAST(195000 AS Decimal(18, 0)), 70, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (12, N'Kính Vạn Hoa (Trọn bộ)', N'9786042167890', 4, N'Tác phẩm thiếu nhi kinh điển của Nguyễn Nhật Ánh về những câu chuyện học đường hóm hỉnh.', N'kinh-van-hoa.jpg', CAST(450000 AS Decimal(18, 0)), CAST(580000 AS Decimal(18, 0)), 25, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (13, N'Tâm Lý Học Tội Phạm', N'9786043312456', 3, N'Phân tích sâu về hành vi và tư duy của tội phạm qua các vụ án thực tế.', N'tam-ly-hoc-toi-pham.jpg', CAST(110000 AS Decimal(18, 0)), CAST(150000 AS Decimal(18, 0)), 35, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (14, N'Ngữ Văn lớp 10 - Kết nối tri thức', N'9786040312543', 7, N'Sách giáo khoa Ngữ Văn lớp 10 theo chương trình giáo dục phổ thông mới.', N'ngu-van-10.jpg', CAST(18000 AS Decimal(18, 0)), CAST(25000 AS Decimal(18, 0)), 500, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (15, N'Giáo Trình Hán Ngữ Tập 1', N'9786043021459', 2, N'Bộ giáo trình chuẩn giúp người mới bắt đầu làm quen với tiếng Hoa cơ bản.', N'han-ngu-1.jpg', CAST(65000 AS Decimal(18, 0)), CAST(85000 AS Decimal(18, 0)), 100, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (16, N'Bắt Đầu Với Câu Hỏi Tại Sao', N'9786049448836', 5, N'Cuốn sách giúp định nghĩa lại cách lãnh đạo bằng việc tìm ra mục đích cốt lõi của mọi hành động.', N'bat-dau-voi-cau-hoi-tai-sao.jpg', CAST(110000 AS Decimal(18, 0)), CAST(155000 AS Decimal(18, 0)), 45, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (17, N'Chiến Tranh Tiền Tệ', N'9786045624325', 8, N'Phơi bày những bí mật của các dòng tiền thế giới và sức mạnh của các định chế tài chính.', N'chien-tran-tien-te.jpg', CAST(180000 AS Decimal(18, 0)), CAST(245000 AS Decimal(18, 0)), 30, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (18, N'Bách Khoa Thư Về Khoa Học', N'9786042223456', 4, N'Cuốn sách minh họa sinh động về các hiện tượng khoa học, từ nguyên tử đến vũ trụ bao la.', N'bach-khoa-khoa-hoc.jpg', CAST(320000 AS Decimal(18, 0)), CAST(395000 AS Decimal(18, 0)), 20, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (19, N'Dám Bị Ghét', N'9786045656784', 5, N'Triết lý tâm lý học Adler giúp bạn tìm thấy tự do và hạnh phúc trong các mối quan hệ xã hội.', N'dam-bi-ghet.jpg', CAST(70000 AS Decimal(18, 0)), CAST(96000 AS Decimal(18, 0)), 85, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (20, N'Cha Voi', N'9786041123457', 1, N'Những chia sẻ tâm huyết của GS Trương Nguyện Thành về cách nuôi dạy con trong thời đại mới.', N'cha-voi.jpg', CAST(95000 AS Decimal(18, 0)), CAST(135000 AS Decimal(18, 0)), 50, 7, N'DangBan')
GO
INSERT [dbo].[Sach] ([MaID], [TenSach], [ISBN], [MaNXB], [MoTa], [HinhAnh], [GiaNhap], [GiaBan], [SoLuongTon], [SoNgayDoiTra], [TrangThai]) VALUES (21, N'Minna No Nihongo Sơ Cấp 1', N'9786043021458', 5, N'Giáo trình chuẩn quốc tế dành cho người mới bắt đầu học tiếng Nhật.', N'minna-1.jpg', CAST(80000 AS Decimal(18, 0)), CAST(110000 AS Decimal(18, 0)), 150, 7, N'DangBan')
GO
SET IDENTITY_INSERT [dbo].[Sach] OFF
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (1, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (2, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (3, 2)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (4, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (5, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (6, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (7, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (8, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (9, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (10, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (11, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (12, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (13, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (14, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (15, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (16, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (17, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (18, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (19, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (20, 1)
GO
INSERT [dbo].[Sach_NCC] ([MaSach], [MaNCC]) VALUES (21, 1)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (1, 1)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (2, 2)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (3, 3)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (4, 7)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (5, 7)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (6, 5)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (7, 5)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (8, 4)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (9, 6)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (10, 9)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (11, 10)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (12, 8)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (13, 11)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (14, 11)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (15, 11)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (16, 12)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (17, 11)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (18, 13)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (19, 15)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (20, 14)
GO
INSERT [dbo].[Sach_TacGia] ([MaSach], [MaTacGia]) VALUES (21, 11)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (1, 2)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (2, 25)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (3, 2)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (4, 13)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (5, 17)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (6, 22)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (7, 20)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (8, 31)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (9, 35)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (10, 7)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (11, 15)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (12, 26)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (13, 28)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (14, 33)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (15, 36)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (16, 12)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (17, 19)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (18, 21)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (19, 29)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (20, 30)
GO
INSERT [dbo].[Sach_TheLoai] ([MaSach], [MaLoai]) VALUES (21, 37)
GO
SET IDENTITY_INSERT [dbo].[TacGia] ON 
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (1, N'Đoàn Giỏi', CAST(N'1925-05-17' AS Date), N'Việt Nam', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (2, N'Dale Carnegie', CAST(N'1888-11-24' AS Date), N'Mỹ', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (3, N'Paulo Coelho', CAST(N'1947-08-24' AS Date), N'Brazil', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (4, N'Ngô Tất Tố', CAST(N'1893-01-01' AS Date), N'Việt Nam', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (5, N'Tony Buổi Sáng', CAST(N'1980-01-01' AS Date), N'Việt Nam', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (6, N'Fujiko F. Fujio', CAST(N'1933-12-01' AS Date), N'Nhật Bản', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (7, N'Raymond Murphy', CAST(N'1945-01-01' AS Date), N'Vương Quốc Anh', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (8, N'Philip Kotler', CAST(N'1931-05-27' AS Date), N'Mỹ', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (9, N'Nguyễn Nhật Ánh', CAST(N'1955-05-07' AS Date), N'Việt Nam', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (10, N'Higashino Keigo', CAST(N'1958-02-04' AS Date), N'Nhật Bản', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (11, N'Robert Kiyosaki', CAST(N'1947-04-08' AS Date), N'Mỹ', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (12, N'Nhiều tác giả', CAST(N'1990-01-01' AS Date), N'Việt Nam', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (13, N'Simon Sinek', CAST(N'1973-10-09' AS Date), N'Anh/Mỹ', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (14, N'DK Publishing', CAST(N'1974-01-01' AS Date), N'Vương Quốc Anh', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (15, N'Trương Nguyện Thành', CAST(N'1961-01-01' AS Date), N'Việt Nam', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (16, N'Ichiro Kishimi', CAST(N'1956-01-01' AS Date), N'Nhật Bản', NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (17, N'Nguyễn Nhật Ánh', NULL, NULL, NULL)
GO
INSERT [dbo].[TacGia] ([MaID], [TenTacGia], [NgaySinh], [QuocTich], [HinhAnh]) VALUES (18, N'Dale Carnegie', NULL, NULL, NULL)
GO
SET IDENTITY_INSERT [dbo].[TacGia] OFF
GO
SET IDENTITY_INSERT [dbo].[TaiKhoanKhachHang] ON 
GO
INSERT [dbo].[TaiKhoanKhachHang] ([MaID], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (1, N'khachhang01', N'123456', N'HoatDong', CAST(N'2026-03-09T01:52:01.250' AS DateTime))
GO
INSERT [dbo].[TaiKhoanKhachHang] ([MaID], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (2, N'khachhang02', N'123456', N'HoatDong', CAST(N'2026-03-09T01:52:01.250' AS DateTime))
GO
INSERT [dbo].[TaiKhoanKhachHang] ([MaID], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (3, N'thangcuoi', N'123456', N'HoatDong', CAST(N'2026-03-09T01:52:01.250' AS DateTime))
GO
INSERT [dbo].[TaiKhoanKhachHang] ([MaID], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (8, N'0123456789', N'123', N'HoatDong', CAST(N'2026-03-11T01:33:20.043' AS DateTime))
GO
INSERT [dbo].[TaiKhoanKhachHang] ([MaID], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (9, N'0901234567', N'123', N'HoatDong', CAST(N'2026-03-11T04:52:45.767' AS DateTime))
GO
INSERT [dbo].[TaiKhoanKhachHang] ([MaID], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (10, N'9876543210', N'123', N'HoatDong', CAST(N'2026-03-11T07:21:13.107' AS DateTime))
GO
INSERT [dbo].[TaiKhoanKhachHang] ([MaID], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (11, N'0989846511', N'123', N'HoatDong', CAST(N'2026-03-13T04:38:24.047' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[TaiKhoanKhachHang] OFF
GO
SET IDENTITY_INSERT [dbo].[TaiKhoanNhanVien] ON 
GO
INSERT [dbo].[TaiKhoanNhanVien] ([MaID], [MaQuyen], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (1, 1, N'admin', N'123456', N'HoatDong', CAST(N'2026-03-08T06:23:20.827' AS DateTime))
GO
INSERT [dbo].[TaiKhoanNhanVien] ([MaID], [MaQuyen], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (5, 3, N'banhang', N'123456', N'HoatDong', CAST(N'2026-03-08T18:39:21.180' AS DateTime))
GO
INSERT [dbo].[TaiKhoanNhanVien] ([MaID], [MaQuyen], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (6, 4, N'nhaphang', N'123456', N'HoatDong', CAST(N'2026-03-08T18:39:54.843' AS DateTime))
GO
INSERT [dbo].[TaiKhoanNhanVien] ([MaID], [MaQuyen], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (8, 3, N'thukho', N'123456', N'HoatDong', CAST(N'2026-03-08T23:44:21.287' AS DateTime))
GO
INSERT [dbo].[TaiKhoanNhanVien] ([MaID], [MaQuyen], [TenDangNhap], [MatKhau], [TrangThai], [NgayTao]) VALUES (9, 2, N'banhang1', N'123456', N'HoatDong', CAST(N'2026-03-09T01:13:24.053' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[TaiKhoanNhanVien] OFF
GO
SET IDENTITY_INSERT [dbo].[TheLoai] ON 
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (1, N'Tác Phẩm Kinh Điển', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (2, N'Tiểu Thuyết', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (3, N'Truyện Ngắn', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (4, N'Ngôn Tình', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (5, N'Du ký - Bút Ký - Tập Bút', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (6, N'Thơ Ca - Ca Dao - Tục Ngữ', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (7, N'Trinh Thám - Kiếm Hiệp - Tội Phạm', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (8, N'Truyện Cổ Tích - Ngụ Ngôn', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (9, N'Huyền Bí - Giả Tưởng - Kinh Dị', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (10, N'Phóng Sự - Phê Bình Văn Học', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (11, N'Tác Giả - Tác Phẩm', 1)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (12, N'Quản trị - Lãnh Đạo', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (13, N'Marketing - Bán hàng', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (14, N'Phân Tích Kinh Tế', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (15, N'Khởi Nghiệp - Làm Giàu', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (16, N'Kinh Doanh', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (17, N'Chứng Khoán - Bất Động Sản - Đầu Tư', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (18, N'Nhân Sự - Việc Làm', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (19, N'Tài chính - Kê Toán - Tiền Tệ', 2)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (20, N'Truyện Thiếu Nhi', 3)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (21, N'Kiến Thức Bách Khoa Cho Trẻ', 3)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (22, N'Manga - Comic', 3)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (23, N'Kiến Thức Kỹ Năng Sống', 3)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (24, N'Tô Màu - Luyện Chữ - Dán Hình', 3)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (25, N'Từ Điển Thiếu Nhi', 3)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (26, N'Phát Triển Kỹ Năng - Trí Tuệ', 3)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (27, N'Kỹ Năng Sống', 4)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (28, N'Tâm Lý Học', 4)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (29, N'Rèn Luyện Nhân Cách', 4)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (30, N'Sách Cho Tuổi Mới Lớn', 4)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (31, N'Hướng Nghiệp & Phát Triển bản Thân', 4)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (32, N'Sách Tham Khảo', 5)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (33, N'Sách Giáo Khoa', 5)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (34, N'Giáo Trình', 5)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (35, N'Tiếng Anh', 6)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (36, N'Tiếng Trung', 6)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (37, N'Tiếng Nhật', 6)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (38, N'Tiếng Hàn', 6)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (39, N'Tiếng Pháp', 6)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (40, N'Tiếng Đức', 6)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (41, N'Tiếng Việt Cho Người Nước Ngoài', 6)
GO
INSERT [dbo].[TheLoai] ([MaID], [TenLoai], [MaDanhMuc]) VALUES (42, N'Ngoại Ngữ Khác', 6)
GO
SET IDENTITY_INSERT [dbo].[TheLoai] OFF
GO
/****** Object:  Index [UQ__ChiTietD__EC06D1224F8A9D8F]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[ChiTietDonHang] ADD UNIQUE NONCLUSTERED 
(
	[MaDH] ASC,
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__ChiTietG__EC06F9C69C6E6572]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[ChiTietGioHang] ADD UNIQUE NONCLUSTERED 
(
	[MaGH] ASC,
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__ChucVu__27258E7711A6551B]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[ChucVu] ADD UNIQUE NONCLUSTERED 
(
	[MaCV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__DanhMuc__B375088678E7944E]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[DanhMuc] ADD UNIQUE NONCLUSTERED 
(
	[MaDanhMuc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__DonHang__27258660F67E14D5]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[DonHang] ADD UNIQUE NONCLUSTERED 
(
	[MaDH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__GioHang__2725AE842AFB28EB]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[GioHang] ADD UNIQUE NONCLUSTERED 
(
	[MaGH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__GioHang__2725CF1F8C6B6705]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[GioHang] ADD UNIQUE NONCLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__HoaDon__27258660A5E4FAAD]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[HoaDon] ADD UNIQUE NONCLUSTERED 
(
	[MaDH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__HoaDon__2725A6E13E917B24]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[HoaDon] ADD UNIQUE NONCLUSTERED 
(
	[MaHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__KhachHan__0389B7BD8B332BB7]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[KhachHang] ADD UNIQUE NONCLUSTERED 
(
	[SoDienThoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__KhachHan__2725CF1F8227D4B8]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[KhachHang] ADD UNIQUE NONCLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_KhachHang_Email]    Script Date: 3/15/2026 11:15:56 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_KhachHang_Email] ON [dbo].[KhachHang]
(
	[Email] ASC
)
WHERE ([Email] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ_KhachHang_TaiKhoan]    Script Date: 3/15/2026 11:15:56 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_KhachHang_TaiKhoan] ON [dbo].[KhachHang]
(
	[MaTaiKhoanKH] ASC
)
WHERE ([MaTaiKhoanKH] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__KhuyenMa__2725CF14BB7A2982]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[KhuyenMai] ADD UNIQUE NONCLUSTERED 
(
	[MaKM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__LichSuKh__3B983FF443351FD8]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[LichSuKho] ADD UNIQUE NONCLUSTERED 
(
	[MaLSK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhaCungC__3A185DEA72357746]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[NhaCungCap] ADD UNIQUE NONCLUSTERED 
(
	[MaNCC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__0389B7BDD8995D55]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[SoDienThoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__2725D70B7CC143B0]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__NhanVien__A5A386F211342B46]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[MaTaiKhoanNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__A9D1053457EFBEEB]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhaXuatB__3A19482D2CB50C50]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[NhaXuatBan] ADD UNIQUE NONCLUSTERED 
(
	[MaNXB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__PhanQuye__2725E7F25F8C7857]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[PhanQuyen] ADD UNIQUE NONCLUSTERED 
(
	[MaPQ] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__PhieuNha__2725E7F1180AAE5B]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[PhieuNhap] ADD UNIQUE NONCLUSTERED 
(
	[MaPN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__PhieuTra__3AE3D6DF58691444]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[PhieuTraKhachHang] ADD UNIQUE NONCLUSTERED 
(
	[MaPTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__PhieuTra__3AE3D6DC9D442DF1]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[PhieuTraNhaCungCap] ADD UNIQUE NONCLUSTERED 
(
	[MaPTN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Sach__447D36EAF1FC2379]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[Sach] ADD UNIQUE NONCLUSTERED 
(
	[ISBN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Sach__B235742C12F9B632]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[Sach] ADD UNIQUE NONCLUSTERED 
(
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__TacGia__27250075F8B26AE6]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[TacGia] ADD UNIQUE NONCLUSTERED 
(
	[MaTG] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__TaiKhoan__55F68FC04F288034]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[TaiKhoanKhachHang] ADD UNIQUE NONCLUSTERED 
(
	[TenDangNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__TaiKhoan__B50CD23365FE09F9]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[TaiKhoanKhachHang] ADD UNIQUE NONCLUSTERED 
(
	[MaTKKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__TaiKhoan__55F68FC03620D7C6]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[TaiKhoanNhanVien] ADD UNIQUE NONCLUSTERED 
(
	[TenDangNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__TaiKhoan__B50CFB8F3B160190]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[TaiKhoanNhanVien] ADD UNIQUE NONCLUSTERED 
(
	[MaTKNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__TheLoai__730A5758AB8F2687]    Script Date: 3/15/2026 11:15:56 PM ******/
ALTER TABLE [dbo].[TheLoai] ADD UNIQUE NONCLUSTERED 
(
	[MaLoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChiTietDonHang] ADD  DEFAULT ((1)) FOR [SoLuong]
GO
ALTER TABLE [dbo].[ChiTietGioHang] ADD  DEFAULT ((1)) FOR [SoLuong]
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap] ADD  DEFAULT ((1)) FOR [SoLuong]
GO
ALTER TABLE [dbo].[ChiTietTraKhachHang] ADD  DEFAULT ((1)) FOR [SoLuong]
GO
ALTER TABLE [dbo].[ChiTietTraKhachHang] ADD  DEFAULT (N'Lỗi NSX') FOR [TinhTrangSach]
GO
ALTER TABLE [dbo].[ChiTietTraNhaCungCap] ADD  DEFAULT ((1)) FOR [SoLuong]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [TongTien]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [TienGiam]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [ThanhTien]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [PhiGiaoHang]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ('TaiQuay') FOR [LoaiDonHang]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ('ChoXuLy') FOR [TrangThaiDon]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT (N'Chờ chuẩn bị') FOR [TrangThaiGiaoHang]
GO
ALTER TABLE [dbo].[GioHang] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT (N'Tiền mặt') FOR [HinhThucThanhToan]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT ((0)) FOR [SoTienKhachTra]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT ((0)) FOR [TienThua]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT (getdate()) FOR [NgayThanhToan]
GO
ALTER TABLE [dbo].[KhachHang] ADD  DEFAULT ((0)) FOR [DiemTichLuy]
GO
ALTER TABLE [dbo].[KhachHang] ADD  DEFAULT ('HoatDong') FOR [TrangThai]
GO
ALTER TABLE [dbo].[KhuyenMai] ADD  DEFAULT ((0.00)) FOR [PhanTramGiam]
GO
ALTER TABLE [dbo].[KhuyenMai] ADD  DEFAULT ((0)) FOR [SoTienGiam]
GO
ALTER TABLE [dbo].[KhuyenMai] ADD  DEFAULT ((0)) FOR [DonHangToiThieu]
GO
ALTER TABLE [dbo].[KhuyenMai] ADD  DEFAULT ('HoatDong') FOR [TrangThai]
GO
ALTER TABLE [dbo].[LichSuKho] ADD  DEFAULT ((0)) FOR [SoLuongLoi]
GO
ALTER TABLE [dbo].[LichSuKho] ADD  DEFAULT (getdate()) FOR [NgayGioTao]
GO
ALTER TABLE [dbo].[NhaCungCap] ADD  DEFAULT ('HoatDong') FOR [TrangThai]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlSach]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlThuocTinh]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlBanHang]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlKhuyenMai]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlHoaDon]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlPhieuDoiTra]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlNhapHang]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlPhieuNhap]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlKhachHang]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlNCC]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlNhanVien]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlTaiKhoan]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlPhanQuyen]
GO
ALTER TABLE [dbo].[PhanQuyen] ADD  DEFAULT ((0)) FOR [QlThongKe]
GO
ALTER TABLE [dbo].[PhieuNhap] ADD  DEFAULT ((0)) FOR [TongTien]
GO
ALTER TABLE [dbo].[PhieuNhap] ADD  DEFAULT ('HoanThanh') FOR [TrangThai]
GO
ALTER TABLE [dbo].[PhieuNhap] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[PhieuTraKhachHang] ADD  DEFAULT ((0)) FOR [TienHoan]
GO
ALTER TABLE [dbo].[PhieuTraKhachHang] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[PhieuTraNhaCungCap] ADD  DEFAULT ((0)) FOR [TongTienHoan]
GO
ALTER TABLE [dbo].[PhieuTraNhaCungCap] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[Sach] ADD  DEFAULT ((0)) FOR [GiaNhap]
GO
ALTER TABLE [dbo].[Sach] ADD  DEFAULT ((0)) FOR [GiaBan]
GO
ALTER TABLE [dbo].[Sach] ADD  DEFAULT ((0)) FOR [SoLuongTon]
GO
ALTER TABLE [dbo].[Sach] ADD  DEFAULT ((7)) FOR [SoNgayDoiTra]
GO
ALTER TABLE [dbo].[Sach] ADD  DEFAULT ('DangBan') FOR [TrangThai]
GO
ALTER TABLE [dbo].[TaiKhoanKhachHang] ADD  DEFAULT ('HoatDong') FOR [TrangThai]
GO
ALTER TABLE [dbo].[TaiKhoanKhachHang] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[TaiKhoanNhanVien] ADD  DEFAULT ('HoatDong') FOR [TrangThai]
GO
ALTER TABLE [dbo].[TaiKhoanNhanVien] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[ChiTietDonHang]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
GO
ALTER TABLE [dbo].[ChiTietDonHang]  WITH CHECK ADD FOREIGN KEY([MaDH])
REFERENCES [dbo].[DonHang] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietGioHang]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietGioHang]  WITH CHECK ADD FOREIGN KEY([MaGH])
REFERENCES [dbo].[GioHang] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap]  WITH CHECK ADD FOREIGN KEY([MaPN])
REFERENCES [dbo].[PhieuNhap] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietPhieuTraKhachHang]  WITH CHECK ADD FOREIGN KEY([MaPTKH])
REFERENCES [dbo].[PhieuTraKhachHang] ([MaID])
GO
ALTER TABLE [dbo].[ChiTietTraKhachHang]  WITH CHECK ADD FOREIGN KEY([MaPTK])
REFERENCES [dbo].[PhieuTraKhachHang] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietTraKhachHang]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
GO
ALTER TABLE [dbo].[ChiTietTraNhaCungCap]  WITH CHECK ADD FOREIGN KEY([MaPTN])
REFERENCES [dbo].[PhieuTraNhaCungCap] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietTraNhaCungCap]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD FOREIGN KEY([MaKM])
REFERENCES [dbo].[KhuyenMai] ([MaID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhachHang] ([MaID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[GioHang]  WITH CHECK ADD FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhachHang] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([MaDH])
REFERENCES [dbo].[DonHang] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[KhachHang]  WITH CHECK ADD FOREIGN KEY([MaTaiKhoanKH])
REFERENCES [dbo].[TaiKhoanKhachHang] ([MaID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[LichSuKho]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD FOREIGN KEY([MaChucVu])
REFERENCES [dbo].[ChucVu] ([MaID])
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD FOREIGN KEY([MaTaiKhoanNV])
REFERENCES [dbo].[TaiKhoanNhanVien] ([MaID])
GO
ALTER TABLE [dbo].[PhieuNhap]  WITH CHECK ADD FOREIGN KEY([MaNCC])
REFERENCES [dbo].[NhaCungCap] ([MaID])
GO
ALTER TABLE [dbo].[PhieuNhap]  WITH CHECK ADD FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[PhieuTraKhachHang]  WITH CHECK ADD FOREIGN KEY([MaDH])
REFERENCES [dbo].[DonHang] ([MaID])
GO
ALTER TABLE [dbo].[PhieuTraKhachHang]  WITH CHECK ADD FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaID])
GO
ALTER TABLE [dbo].[PhieuTraNhaCungCap]  WITH CHECK ADD FOREIGN KEY([MaNCC])
REFERENCES [dbo].[NhaCungCap] ([MaID])
GO
ALTER TABLE [dbo].[PhieuTraNhaCungCap]  WITH CHECK ADD FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaID])
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD FOREIGN KEY([MaNXB])
REFERENCES [dbo].[NhaXuatBan] ([MaID])
GO
ALTER TABLE [dbo].[Sach_NCC]  WITH CHECK ADD FOREIGN KEY([MaNCC])
REFERENCES [dbo].[NhaCungCap] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Sach_NCC]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Sach_TacGia]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Sach_TacGia]  WITH CHECK ADD FOREIGN KEY([MaTacGia])
REFERENCES [dbo].[TacGia] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Sach_TheLoai]  WITH CHECK ADD FOREIGN KEY([MaLoai])
REFERENCES [dbo].[TheLoai] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Sach_TheLoai]  WITH CHECK ADD FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TaiKhoanNhanVien]  WITH CHECK ADD FOREIGN KEY([MaQuyen])
REFERENCES [dbo].[PhanQuyen] ([MaID])
GO
ALTER TABLE [dbo].[TheLoai]  WITH CHECK ADD FOREIGN KEY([MaDanhMuc])
REFERENCES [dbo].[DanhMuc] ([MaID])
GO
ALTER TABLE [dbo].[ChiTietDonHang]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[ChiTietGioHang]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[ChiTietTraKhachHang]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[ChiTietTraNhaCungCap]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([LoaiDonHang]='Online' OR [LoaiDonHang]='TaiQuay'))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([TrangThaiDon]='DaHuy' OR [TrangThaiDon]='HoanThanh' OR [TrangThaiDon]='DaXacNhan' OR [TrangThaiDon]='ChoXuLy'))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([TrangThaiGiaoHang]=N'Giao thất bại' OR [TrangThaiGiaoHang]=N'Giao thành công' OR [TrangThaiGiaoHang]=N'Đang giao hàng' OR [TrangThaiGiaoHang]=N'Chờ chuẩn bị'))
GO
ALTER TABLE [dbo].[KhachHang]  WITH CHECK ADD CHECK  (([TrangThai]='NgungHoatDong' OR [TrangThai]='HoatDong'))
GO
ALTER TABLE [dbo].[KhuyenMai]  WITH CHECK ADD CHECK  (([NgayKetThuc]>=[NgayBatDau]))
GO
ALTER TABLE [dbo].[KhuyenMai]  WITH CHECK ADD CHECK  (([TrangThai]='HetHan' OR [TrangThai]='HoatDong'))
GO
ALTER TABLE [dbo].[LichSuKho]  WITH CHECK ADD CHECK  (([LoaiGiaoDich]='PHAN_LOAI_LOI' OR [LoaiGiaoDich]='KIEM_KE' OR [LoaiGiaoDich]='TRA_NCC' OR [LoaiGiaoDich]='KHACH_TRA' OR [LoaiGiaoDich]='BAN_HANG' OR [LoaiGiaoDich]='NHAP_HANG'))
GO
ALTER TABLE [dbo].[LichSuKho]  WITH CHECK ADD CHECK  (([SoLuongLoi]>=(0)))
GO
ALTER TABLE [dbo].[NhaCungCap]  WITH CHECK ADD CHECK  (([TrangThai]='NgungHoatDong' OR [TrangThai]='HoatDong'))
GO
ALTER TABLE [dbo].[PhieuNhap]  WITH CHECK ADD CHECK  (([TrangThai]='DaHuy' OR [TrangThai]='HoanThanh' OR [TrangThai]='ChoXuLy'))
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD CHECK  ((len([ISBN])=(13)))
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD CHECK  (([SoLuongTon]>=(0)))
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD CHECK  (([TrangThai]='NgungBan' OR [TrangThai]='DangBan'))
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD  CONSTRAINT [CK_GiaBan] CHECK  (([GiaBan]>=[GiaNhap]))
GO
ALTER TABLE [dbo].[Sach] CHECK CONSTRAINT [CK_GiaBan]
GO
ALTER TABLE [dbo].[TacGia]  WITH CHECK ADD CHECK  (([NgaySinh]<=getdate()))
GO
ALTER TABLE [dbo].[TaiKhoanKhachHang]  WITH CHECK ADD CHECK  (([TrangThai]='Khoa' OR [TrangThai]='HoatDong'))
GO
ALTER TABLE [dbo].[TaiKhoanNhanVien]  WITH CHECK ADD CHECK  (([TrangThai]='Khoa' OR [TrangThai]='HoatDong'))
GO
USE [master]
GO
ALTER DATABASE [QuanLyCuaHangSach] SET  READ_WRITE 
GO
