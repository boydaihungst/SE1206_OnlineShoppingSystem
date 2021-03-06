USE [SE1206_OnlineShoppingSystem]
GO
/****** Object:  Table [dbo].[Customers]    Script Date: 29-Jun-18 12:46:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Customers](
	[id] [int] NOT NULL,
	[name] [varchar](150) NOT NULL,
 CONSTRAINT [PK_Customers] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[InvoiceLines]    Script Date: 29-Jun-18 12:46:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InvoiceLines](
	[invoiceid] [int] NOT NULL,
	[productid] [int] NOT NULL,
	[quantity] [float] NOT NULL,
	[unitprice] [float] NOT NULL,
 CONSTRAINT [PK_InvoiceLines] PRIMARY KEY CLUSTERED 
(
	[invoiceid] ASC,
	[productid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Invoices]    Script Date: 29-Jun-18 12:46:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Invoices](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[orderdate] [date] NOT NULL,
	[paymentmethod] [varchar](150) NOT NULL,
	[customerid] [int] NOT NULL,
	[status] [nvarchar](50) NULL,
 CONSTRAINT [PK_Invoices] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Products]    Script Date: 29-Jun-18 12:46:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Products](
	[id] [int] NOT NULL,
	[name] [varchar](150) NOT NULL,
	[price] [float] NOT NULL,
 CONSTRAINT [PK_Products] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Customers] ([id], [name]) VALUES (1, N'Lam Phan')
INSERT [dbo].[Customers] ([id], [name]) VALUES (2, N'Hoa Khuat')
INSERT [dbo].[Customers] ([id], [name]) VALUES (3, N'Bich Thao')
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (1, 1, 2, 10000)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (2, 1, 2, 10000)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (2, 2, 2, 8500)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (2, 3, 2, 8000)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (2, 4, 2, 50000)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (3, 2, 2, 8500)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (3, 3, 2, 8000)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (4, 1, 2, 10000)
INSERT [dbo].[InvoiceLines] ([invoiceid], [productid], [quantity], [unitprice]) VALUES (4, 2, 2, 8500)
SET IDENTITY_INSERT [dbo].[Invoices] ON 

INSERT [dbo].[Invoices] ([id], [orderdate], [paymentmethod], [customerid], [status]) VALUES (1, CAST(N'2018-06-29' AS Date), N'Cash', 1, N'open')
INSERT [dbo].[Invoices] ([id], [orderdate], [paymentmethod], [customerid], [status]) VALUES (2, CAST(N'2018-06-29' AS Date), N'Cash', 1, N'open')
INSERT [dbo].[Invoices] ([id], [orderdate], [paymentmethod], [customerid], [status]) VALUES (3, CAST(N'2018-06-29' AS Date), N'', 1, N'open')
INSERT [dbo].[Invoices] ([id], [orderdate], [paymentmethod], [customerid], [status]) VALUES (4, CAST(N'2018-06-29' AS Date), N'xxx', 3, N'open')
SET IDENTITY_INSERT [dbo].[Invoices] OFF
INSERT [dbo].[Products] ([id], [name], [price]) VALUES (1, N'cocacola', 10000)
INSERT [dbo].[Products] ([id], [name], [price]) VALUES (2, N'fanta', 8500)
INSERT [dbo].[Products] ([id], [name], [price]) VALUES (3, N'pepsi', 8000)
INSERT [dbo].[Products] ([id], [name], [price]) VALUES (4, N'moster', 50000)
ALTER TABLE [dbo].[InvoiceLines]  WITH CHECK ADD  CONSTRAINT [FK_InvoiceLines_Invoices] FOREIGN KEY([invoiceid])
REFERENCES [dbo].[Invoices] ([id])
GO
ALTER TABLE [dbo].[InvoiceLines] CHECK CONSTRAINT [FK_InvoiceLines_Invoices]
GO
ALTER TABLE [dbo].[InvoiceLines]  WITH CHECK ADD  CONSTRAINT [FK_InvoiceLines_Products] FOREIGN KEY([productid])
REFERENCES [dbo].[Products] ([id])
GO
ALTER TABLE [dbo].[InvoiceLines] CHECK CONSTRAINT [FK_InvoiceLines_Products]
GO
ALTER TABLE [dbo].[Invoices]  WITH CHECK ADD  CONSTRAINT [FK_Invoices_Customers] FOREIGN KEY([customerid])
REFERENCES [dbo].[Customers] ([id])
GO
ALTER TABLE [dbo].[Invoices] CHECK CONSTRAINT [FK_Invoices_Customers]
GO
