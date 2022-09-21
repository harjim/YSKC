using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_info_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_info_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    staffRdHour = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    prodRdHour = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    materialRaw = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    materialdataentry = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    materialReserve = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    yieldAmount = table.Column<decimal>(nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_info_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_p_info_summary_companyId_projectId_month",
                table: "p_info_summary",
                columns: new[] { "companyId", "projectId", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_info_summary");
        }
    }
}
