using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class c_table : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "c_annualReport",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false),
                    reportName = table.Column<string>(nullable: true),
                    filePath = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_annualReport", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_financialCondition",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    businessIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    mainBusinessIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    netProfit = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    grossOfIndustrial = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    addedOfIndustrial = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    totalAssets = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    totalFixedAssets = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    netAssets = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    fixedAssetsOfInvestment = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    netTotalCashFlow = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    netCashFlowOfOperating = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    assetLiabilityRatio = table.Column<decimal>(type: "decimal(6,2)", nullable: false),
                    totalExpenditureOfRD = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    loanAmountOfGovernment = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    dueLoanOfGovernment = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    totalTax = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    corporateIncomeTax = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_financialCondition", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_ownership",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    shareholder = table.Column<string>(maxLength: 50, nullable: false),
                    capitalContribution = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    contributionType = table.Column<string>(maxLength: 100, nullable: false),
                    proportion = table.Column<decimal>(type: "decimal(5,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_ownership", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_support",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    projectName = table.Column<string>(maxLength: 200, nullable: false),
                    startTime = table.Column<DateTime>(nullable: false),
                    endTime = table.Column<DateTime>(nullable: false),
                    supportTime = table.Column<DateTime>(nullable: false),
                    supportDeptName = table.Column<string>(maxLength: 100, nullable: false),
                    supportAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    checkTime = table.Column<DateTime>(nullable: false),
                    checkResult = table.Column<string>(maxLength: 100, nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_support", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_annualReport");

            migrationBuilder.DropTable(
                name: "c_financialCondition");

            migrationBuilder.DropTable(
                name: "c_ownership");

            migrationBuilder.DropTable(
                name: "c_support");
        }
    }
}
