using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createCompanyExtraTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "issuceNum",
                table: "c_support",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "master",
                table: "c_support",
                maxLength: 30,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "c_bank_info",
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
                    compnyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    bankName = table.Column<string>(maxLength: 100, nullable: true),
                    bankAccount = table.Column<string>(maxLength: 50, nullable: true),
                    accountName = table.Column<string>(maxLength: 50, nullable: true),
                    creditRating = table.Column<int>(nullable: true),
                    industry = table.Column<string>(maxLength: 50, nullable: true),
                    filledItems = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_bank_info", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_employment_info",
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
                    compnyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    corporationName = table.Column<string>(maxLength: 30, nullable: true),
                    corporationDegree = table.Column<int>(nullable: true),
                    corporationPhone = table.Column<string>(maxLength: 20, nullable: true),
                    corporationIdNum = table.Column<string>(maxLength: 30, nullable: true),
                    linkName = table.Column<string>(maxLength: 30, nullable: true),
                    linkDegree = table.Column<int>(nullable: true),
                    linkPhone = table.Column<string>(maxLength: 20, nullable: true),
                    linkIdNum = table.Column<string>(maxLength: 30, nullable: true),
                    employees = table.Column<int>(nullable: true),
                    females = table.Column<int>(nullable: true),
                    returnees = table.Column<int>(nullable: true),
                    insurances = table.Column<int>(nullable: true),
                    foreignExperts = table.Column<int>(nullable: true),
                    freshGraduates = table.Column<int>(nullable: true),
                    administrations = table.Column<int>(nullable: true),
                    marketings = table.Column<int>(nullable: true),
                    rdDesigners = table.Column<int>(nullable: true),
                    processes = table.Column<int>(nullable: true),
                    otherWorkers = table.Column<int>(nullable: true),
                    doctors = table.Column<int>(nullable: true),
                    masters = table.Column<int>(nullable: true),
                    undergraduates = table.Column<int>(nullable: true),
                    juniorColleges = table.Column<int>(nullable: true),
                    otherDegrees = table.Column<int>(nullable: true),
                    highTitles = table.Column<int>(nullable: true),
                    middleTitles = table.Column<int>(nullable: true),
                    primaryTitles = table.Column<int>(nullable: true),
                    otherTitles = table.Column<int>(nullable: true),
                    filledItems = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_employment_info", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_extra",
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
                    compnyId = table.Column<int>(nullable: false),
                    manageAddrCode = table.Column<string>(maxLength: 30, nullable: true),
                    companyAddress = table.Column<string>(maxLength: 100, nullable: true),
                    registerAddrCode = table.Column<string>(maxLength: 30, nullable: true),
                    registerAddress = table.Column<string>(maxLength: 30, nullable: true),
                    registerType = table.Column<string>(maxLength: 30, nullable: true),
                    mainProducts = table.Column<string>(maxLength: 50, nullable: true),
                    officeAddrCode = table.Column<string>(maxLength: 30, nullable: true),
                    cooperateOrg = table.Column<int>(nullable: true),
                    insideRdOrg = table.Column<int>(nullable: true),
                    productAddrCode = table.Column<string>(maxLength: 30, nullable: true),
                    officeArea = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    overSeaMarketings = table.Column<int>(nullable: true),
                    yearKWh = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    productArea = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    overSeaRdOrg = table.Column<int>(nullable: true),
                    yearMwo = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    onlineReport = table.Column<bool>(nullable: true),
                    ownerECP = table.Column<bool>(nullable: true),
                    nameECP = table.Column<string>(maxLength: 50, nullable: true),
                    urlECP = table.Column<string>(maxLength: 200, nullable: true),
                    qualification = table.Column<string>(maxLength: 50, nullable: true),
                    filledItems = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_extra", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_financial_info",
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
                    value = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    key = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_financial_info", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_bank_info");

            migrationBuilder.DropTable(
                name: "c_employment_info");

            migrationBuilder.DropTable(
                name: "c_extra");

            migrationBuilder.DropTable(
                name: "c_financial_info");

            migrationBuilder.DropColumn(
                name: "issuceNum",
                table: "c_support");

            migrationBuilder.DropColumn(
                name: "master",
                table: "c_support");
        }
    }
}
