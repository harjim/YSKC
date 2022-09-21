using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createHigh_scoreTableAndAddP_reportEmployeeAmountCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "employeeAmount",
                table: "p_report",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "high_score",
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
                    table.PrimaryKey("PK_high_score", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_high_score_companyId_year",
                table: "high_score",
                columns: new[] { "companyId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "high_score");

            migrationBuilder.DropColumn(
                name: "employeeAmount",
                table: "p_report");
        }
    }
}
