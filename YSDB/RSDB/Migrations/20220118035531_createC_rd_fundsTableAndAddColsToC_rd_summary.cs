using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createC_rd_fundsTableAndAddColsToC_rd_summary : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "selected",
                table: "p_yield_config",
                nullable: false,
                defaultValue: true);

            migrationBuilder.AddColumn<int>(
                name: "achievementCount",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "amount",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k10000",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k10100",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20000",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20100",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20200",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20300",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20500",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20600",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20700",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k30000",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k40000",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k40200",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k50000",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k69900",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "lastRdCount",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "levelFileCount",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "nextRdCount",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "patentCount",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "proposalCount",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "c_rd_funds",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
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
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_rd_funds", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_rd_funds_companyId_year_month_type",
                table: "c_rd_funds",
                columns: new[] { "companyId", "year", "month", "type" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_rd_funds");

            migrationBuilder.DropColumn(
                name: "selected",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "achievementCount",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "amount",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k10000",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k10100",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k20000",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k20100",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k20200",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k20300",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k20500",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k20600",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k20700",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k30000",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k40000",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k40200",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k50000",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "k69900",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "lastRdCount",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "levelFileCount",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "nextRdCount",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "patentCount",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "proposalCount",
                table: "c_rd_summary");
        }
    }
}
