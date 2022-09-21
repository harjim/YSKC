using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createc_documentAndRevenueAndYear_costTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "accountType",
                table: "p_report",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "revenueFcst",
                table: "p_report",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "c_document",
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
                    type = table.Column<int>(nullable: false),
                    data = table.Column<string>(type: "text", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_document", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_revenue",
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
                    month = table.Column<DateTime>(nullable: false),
                    revenue = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_revenue", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_year_cost",
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
                    rdType = table.Column<int>(nullable: false),
                    cost = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_year_cost", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_document_companyId_year_type",
                table: "c_document",
                columns: new[] { "companyId", "year", "type" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_c_revenue_companyId_year_month",
                table: "c_revenue",
                columns: new[] { "companyId", "year", "month" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_c_year_cost_companyId_year_rdType",
                table: "c_year_cost",
                columns: new[] { "companyId", "year", "rdType" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_document");

            migrationBuilder.DropTable(
                name: "c_revenue");

            migrationBuilder.DropTable(
                name: "c_year_cost");

            migrationBuilder.DropColumn(
                name: "accountType",
                table: "p_report");

            migrationBuilder.DropColumn(
                name: "revenueFcst",
                table: "p_report");
        }
    }
}
