using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createHigh_tech_scoreAndHigh_finance_scoreTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "high_score");

            migrationBuilder.CreateTable(
                name: "high_finance_score",
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
                    salesYear1 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    salesYear2 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    salesYear3 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    totalSales = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    NAVYear1 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    NAVYear2 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    NAVYear3 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    income = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    NAVScore = table.Column<int>(nullable: false),
                    salesScore = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_high_finance_score", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "high_tech_score",
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
                    rdCnt = table.Column<int>(nullable: true),
                    rdFunds = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    highTechCnt = table.Column<int>(nullable: true),
                    highTechCodes = table.Column<string>(maxLength: 1000, nullable: true),
                    income = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    advanced = table.Column<int>(nullable: true),
                    effect = table.Column<int>(nullable: true),
                    patentAmount = table.Column<int>(nullable: true),
                    acquirement = table.Column<int>(nullable: true),
                    acquirementMode = table.Column<int>(nullable: true),
                    contribution = table.Column<int>(nullable: true),
                    scienceResult = table.Column<int>(nullable: true),
                    generalLedger = table.Column<int>(nullable: true),
                    cooperation = table.Column<int>(nullable: true),
                    excitation = table.Column<int>(nullable: true),
                    foster = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_high_tech_score", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_high_finance_score_companyId_year",
                table: "high_finance_score",
                columns: new[] { "companyId", "year" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_high_tech_score_companyId_year",
                table: "high_tech_score",
                columns: new[] { "companyId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "high_finance_score");

            migrationBuilder.DropTable(
                name: "high_tech_score");

            migrationBuilder.CreateTable(
                name: "high_score",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    acquirement = table.Column<int>(nullable: true),
                    acquirementMode = table.Column<int>(nullable: true),
                    advanced = table.Column<int>(nullable: true),
                    companyId = table.Column<int>(nullable: false),
                    contribution = table.Column<int>(nullable: true),
                    cooperation = table.Column<int>(nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    effect = table.Column<int>(nullable: true),
                    excitation = table.Column<int>(nullable: true),
                    foster = table.Column<int>(nullable: true),
                    generalLedger = table.Column<int>(nullable: true),
                    highTechCnt = table.Column<int>(nullable: true),
                    highTechCodes = table.Column<string>(maxLength: 1000, nullable: true),
                    income = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    patentAmount = table.Column<int>(nullable: true),
                    rdCnt = table.Column<int>(nullable: true),
                    rdFunds = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    scienceResult = table.Column<int>(nullable: true),
                    year = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_high_score", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_high_score_companyId_year",
                table: "high_score",
                columns: new[] { "companyId", "year" },
                unique: true);
        }
    }
}
