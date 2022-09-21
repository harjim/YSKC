using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class createC_highTechTableAndAddIpNumsCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rdFiexdAssets",
                table: "c_year_info",
                newName: "taxIncome");

            migrationBuilder.AddColumn<int>(
                name: "authIpNum",
                table: "c_year_info",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "designPatentNum",
                table: "c_year_info",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<decimal>(
                name: "highIncome",
                table: "c_year_info",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "softNum",
                table: "c_year_info",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "utilityPatentNum",
                table: "c_year_info",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<decimal>(
                name: "unFunds",
                table: "c_project_year",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.CreateTable(
                name: "c_highTech",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    hpName = table.Column<string>(maxLength: 200, nullable: false),
                    tecIndustry = table.Column<string>(maxLength: 20, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_highTech", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_highTech");

            migrationBuilder.DropColumn(
                name: "authIpNum",
                table: "c_year_info");

            migrationBuilder.DropColumn(
                name: "designPatentNum",
                table: "c_year_info");

            migrationBuilder.DropColumn(
                name: "highIncome",
                table: "c_year_info");

            migrationBuilder.DropColumn(
                name: "softNum",
                table: "c_year_info");

            migrationBuilder.DropColumn(
                name: "utilityPatentNum",
                table: "c_year_info");

            migrationBuilder.DropColumn(
                name: "unFunds",
                table: "c_project_year");

            migrationBuilder.RenameColumn(
                name: "taxIncome",
                table: "c_year_info",
                newName: "rdFiexdAssets");
        }
    }
}
