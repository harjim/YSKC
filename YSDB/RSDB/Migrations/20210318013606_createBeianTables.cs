using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createBeianTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "t_bankReceipt",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    transfer = table.Column<bool>(nullable: false),
                    transferDate = table.Column<DateTime>(nullable: true),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    payee = table.Column<string>(maxLength: 50, nullable: false),
                    voucherNo = table.Column<string>(maxLength: 50, nullable: false),
                    payDate = table.Column<DateTime>(nullable: false),
                    voucherPath = table.Column<string>(maxLength: 200, nullable: true),
                    bankReceiptPath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_bankReceipt", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_beian",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    reportName = table.Column<string>(maxLength: 200, nullable: true),
                    pname = table.Column<string>(nullable: true),
                    constructionPlace = table.Column<string>(maxLength: 200, nullable: true),
                    economyType = table.Column<int>(nullable: true),
                    content = table.Column<string>(maxLength: 2000, nullable: true),
                    beginDate = table.Column<DateTime>(nullable: true),
                    endDate = table.Column<DateTime>(nullable: true),
                    beianNum = table.Column<string>(maxLength: 50, nullable: true),
                    beianDate = table.Column<DateTime>(nullable: true),
                    filePath = table.Column<string>(maxLength: 200, nullable: true),
                    scanFilePath = table.Column<string>(maxLength: 200, nullable: true),
                    totalAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    equipment = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    initWorkCapital = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    construction = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    completeRate = table.Column<decimal>(type: "decimal(4,2)", nullable: true),
                    change = table.Column<bool>(nullable: false),
                    changeLetterDate = table.Column<DateTime>(nullable: true),
                    changeContent = table.Column<string>(maxLength: 2000, nullable: true),
                    changeFilePath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_beian", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_contract",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    contractNo = table.Column<string>(maxLength: 50, nullable: false),
                    signTarget = table.Column<string>(maxLength: 50, nullable: false),
                    filePath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_contract", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_contract_detail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    contactId = table.Column<int>(nullable: false),
                    ename = table.Column<string>(maxLength: 200, nullable: false),
                    emodal = table.Column<string>(maxLength: 100, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_contract_detail", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_equipment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    beianId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    ename = table.Column<string>(maxLength: 200, nullable: false),
                    emodal = table.Column<string>(maxLength: 100, nullable: false),
                    producePlace = table.Column<string>(maxLength: 200, nullable: false),
                    unitPrice = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    quantity = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    unit = table.Column<string>(maxLength: 10, nullable: false),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_equipment", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_investment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false),
                    ename = table.Column<string>(maxLength: 200, nullable: false),
                    entryDate = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_investment", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_investment_bankReceipt",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    investmentId = table.Column<int>(nullable: false),
                    bankReceiptId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_investment_bankReceipt", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_investment_contract",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    investmentId = table.Column<int>(nullable: false),
                    contactDetailId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_investment_contract", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_investment_invoice",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    investmentId = table.Column<int>(nullable: false),
                    invoiceDetailId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_investment_invoice", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_invoice",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    invoiceNo = table.Column<string>(maxLength: 50, nullable: false),
                    taxAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    invoiceFrom = table.Column<string>(maxLength: 50, nullable: false),
                    invoiceDate = table.Column<DateTime>(nullable: false),
                    invoicePath = table.Column<string>(maxLength: 200, nullable: true),
                    voucherNo = table.Column<string>(maxLength: 50, nullable: false),
                    voucherPath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_invoice", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_invoice_detail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    invoiceId = table.Column<int>(nullable: false),
                    ename = table.Column<string>(maxLength: 200, nullable: false),
                    emodal = table.Column<string>(maxLength: 100, nullable: false),
                    quantity = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    excludeTaxPrice = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    taxRate = table.Column<decimal>(type: "decimal(4,2)", nullable: false),
                    taxPrice = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    excludeTaxAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    taxAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_invoice_detail", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_nameplate",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    investmentId = table.Column<int>(nullable: false),
                    ename = table.Column<string>(maxLength: 200, nullable: false),
                    emodal = table.Column<string>(maxLength: 100, nullable: false),
                    manufacturer = table.Column<string>(maxLength: 200, nullable: false),
                    factoryDate = table.Column<DateTime>(nullable: false),
                    factoryNo = table.Column<string>(maxLength: 100, nullable: false),
                    filePath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_nameplate", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_project_beian",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    beianId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_project_beian", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "t_bankReceipt");

            migrationBuilder.DropTable(
                name: "t_beian");

            migrationBuilder.DropTable(
                name: "t_contract");

            migrationBuilder.DropTable(
                name: "t_contract_detail");

            migrationBuilder.DropTable(
                name: "t_equipment");

            migrationBuilder.DropTable(
                name: "t_investment");

            migrationBuilder.DropTable(
                name: "t_investment_bankReceipt");

            migrationBuilder.DropTable(
                name: "t_investment_contract");

            migrationBuilder.DropTable(
                name: "t_investment_invoice");

            migrationBuilder.DropTable(
                name: "t_invoice");

            migrationBuilder.DropTable(
                name: "t_invoice_detail");

            migrationBuilder.DropTable(
                name: "t_nameplate");

            migrationBuilder.DropTable(
                name: "t_project_beian");
        }
    }
}
