using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createC_rd_fundsAndAddProject_summary_dataCntCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "achievementCnt",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "costAmount",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k10000",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k10100",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20000",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20100",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20200",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20300",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20500",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20600",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k20700",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k30000",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k40000",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k40200",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k50000",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "k69900",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "levelFileCnt",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "nextRdCnt",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "proposalCnt",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "revenue",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

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
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    revenue = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
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
                name: "achievementCnt",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "costAmount",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k10000",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k10100",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k20000",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k20100",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k20200",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k20300",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k20500",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k20600",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k20700",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k30000",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k40000",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k40200",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k50000",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "k69900",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "levelFileCnt",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "nextRdCnt",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "proposalCnt",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "revenue",
                table: "project_summary_data");
        }
    }
}
