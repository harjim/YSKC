using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createC_rd_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "c_rd_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    rdPlanCount = table.Column<int>(nullable: true),
                    rdCount = table.Column<int>(nullable: true),
                    budget = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    rdFunds = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    rdEmployeeCount = table.Column<int>(nullable: true),
                    rdEquipmentCount = table.Column<int>(nullable: true),
                    docFileCount = table.Column<int>(nullable: true),
                    buildCount = table.Column<int>(nullable: true),
                    highTechCount = table.Column<int>(nullable: true),
                    highTechRatio = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    totalIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_rd_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_rd_summary_companyId_year",
                table: "c_rd_summary",
                columns: new[] { "companyId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_rd_summary");
        }
    }
}
