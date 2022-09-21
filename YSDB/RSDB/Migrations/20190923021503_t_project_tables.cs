using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class t_project_tables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "t_projectAppendix",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    fileName = table.Column<string>(maxLength: 100, nullable: false),
                    filePath = table.Column<string>(maxLength: 200, nullable: false),
                    downloadTimes = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_projectAppendix", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_projectBasic",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    industryCode = table.Column<string>(maxLength: 50, nullable: false),
                    recordNumber = table.Column<string>(maxLength: 50, nullable: false),
                    projectType = table.Column<int>(nullable: false),
                    conssRuctionType = table.Column<int>(nullable: false),
                    technicalField = table.Column<int>(nullable: false),
                    notInvolveRemark = table.Column<string>(nullable: true),
                    mainContents = table.Column<string>(type: "text", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_projectBasic", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_projectImplement",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    businessIncome = table.Column<string>(maxLength: 50, nullable: false),
                    profit = table.Column<string>(maxLength: 50, nullable: false),
                    taxRevenue = table.Column<string>(maxLength: 50, nullable: false),
                    expect = table.Column<string>(maxLength: 100, nullable: false),
                    lineOfInfo = table.Column<string>(maxLength: 50, nullable: false),
                    manpowerSavings = table.Column<string>(maxLength: 100, nullable: false),
                    goodOfRate = table.Column<string>(maxLength: 50, nullable: false),
                    consumptionPer = table.Column<string>(maxLength: 50, nullable: false),
                    carbonDioxide = table.Column<string>(maxLength: 50, nullable: false),
                    unitWaterUse = table.Column<string>(maxLength: 50, nullable: false),
                    casualti = table.Column<string>(maxLength: 50, nullable: false),
                    weedOut = table.Column<string>(maxLength: 50, nullable: false),
                    digitalize = table.Column<string>(maxLength: 50, nullable: false),
                    numericalControl = table.Column<string>(maxLength: 50, nullable: false),
                    talentTeam = table.Column<string>(maxLength: 50, nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_projectImplement", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_projectInvestMent",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    equipmentCostOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    equipmentCostOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    installationCostOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    installationCostOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    constructionCostOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    constructionCostOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    softwareKitOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    softwareKitOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    InspectionOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    InspectionOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    digitalIntegrationOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    digitalIntegrationOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    rdOutsourcingOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    rdOutsourcingOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    waterOfReport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    waterOfPaid = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_projectInvestMent", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_projectOther",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    areaCode = table.Column<string>(maxLength: 20, nullable: false),
                    synopsis = table.Column<string>(maxLength: 300, nullable: false),
                    targetAndContent = table.Column<string>(maxLength: 300, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_projectOther", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_projectQuota",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    investment = table.Column<decimal>(nullable: false),
                    transform = table.Column<decimal>(nullable: false),
                    water = table.Column<decimal>(nullable: false),
                    investmentOfPlan = table.Column<decimal>(nullable: false),
                    transformOfPlan = table.Column<decimal>(nullable: false),
                    waterOfPlan = table.Column<decimal>(nullable: false),
                    robotsCount = table.Column<int>(nullable: false),
                    robotsOfPlan = table.Column<int>(nullable: false),
                    robotsOfAbroad = table.Column<int>(nullable: false),
                    robotsOfDomestic = table.Column<int>(nullable: false),
                    robotsOfGd = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_projectQuota", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "t_projectAppendix");

            migrationBuilder.DropTable(
                name: "t_projectBasic");

            migrationBuilder.DropTable(
                name: "t_projectImplement");

            migrationBuilder.DropTable(
                name: "t_projectInvestMent");

            migrationBuilder.DropTable(
                name: "t_projectOther");

            migrationBuilder.DropTable(
                name: "t_projectQuota");
        }
    }
}
