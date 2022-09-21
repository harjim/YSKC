using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createC_rd_summary_districtAndc_rd_funds_districtTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "c_rd_funds_district",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    deptId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    type = table.Column<int>(nullable: false),
                    k10000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k10100 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k20000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k20100 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k20200 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k20300 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k20500 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k20600 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k20700 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k30000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k40000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k40200 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k50000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    k69900 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    revenue = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_rd_funds_district", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_rd_summary_district",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    deptId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    rdPlanCount = table.Column<int>(nullable: true),
                    rdCount = table.Column<int>(nullable: true),
                    budget = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    accountType = table.Column<int>(nullable: true),
                    rdFee = table.Column<int>(nullable: true),
                    employeeAmount = table.Column<int>(nullable: true),
                    revenueFcst = table.Column<int>(nullable: true),
                    salesRdFee = table.Column<int>(nullable: true),
                    finaMode = table.Column<int>(nullable: true),
                    rdFunds = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    rdEmployeeCount = table.Column<int>(nullable: true),
                    rdEquipmentCount = table.Column<int>(nullable: true),
                    docFileCount = table.Column<int>(nullable: true),
                    buildCount = table.Column<int>(nullable: true),
                    highTechCount = table.Column<int>(nullable: true),
                    highTechIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    totalIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    lastRdCnt = table.Column<int>(nullable: true),
                    nextRdCnt = table.Column<int>(nullable: true),
                    proposalCnt = table.Column<int>(nullable: true),
                    patentCnt = table.Column<int>(nullable: true),
                    achievementCnt = table.Column<int>(nullable: true),
                    levelFileCnt = table.Column<int>(nullable: true),
                    reportCnt = table.Column<int>(nullable: true),
                    c10000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c10100 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c20000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c20100 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c20200 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c20300 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c20500 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c20600 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c20700 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c30000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c40000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c40200 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c50000 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    c69900 = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    costAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    revenue = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_rd_summary_district", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_rd_funds_district_deptId_year_month_type",
                table: "c_rd_funds_district",
                columns: new[] { "deptId", "year", "month", "type" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_c_rd_summary_district_deptId_year",
                table: "c_rd_summary_district",
                columns: new[] { "deptId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_rd_funds_district");

            migrationBuilder.DropTable(
                name: "c_rd_summary_district");
        }
    }
}
