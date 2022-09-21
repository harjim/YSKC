using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class patt : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "remainQuantity",
                table: "d_material",
                type: "decimal(10,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<string>(
                name: "remainEquData",
                table: "d_equipment",
                maxLength: 64,
                nullable: false,
                defaultValue: "00000000000000000000000000000000000000000000000000000000000000");

            migrationBuilder.AddColumn<decimal>(
                name: "remainQuantiy",
                table: "d_energy",
                type: "decimal(10,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<string>(
                name: "remainAttData",
                table: "d_attendance",
                maxLength: 64,
                nullable: false,
                defaultValue: "0000000000000000000000000000000");

            migrationBuilder.CreateTable(
                name: "p_attendance",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    attendanceDataId = table.Column<int>(nullable: false),
                    attData = table.Column<string>(maxLength: 64, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_attendance", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_energy",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    energyDataId = table.Column<int>(nullable: false),
                    used = table.Column<decimal>(type: "decimal(12,2)", nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_energy", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_inspection",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    inspectionDataId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_inspection", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_material",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    materialDataId = table.Column<int>(nullable: false),
                    used = table.Column<decimal>(type: "decimal(12,2)", nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_material", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_attendance");

            migrationBuilder.DropTable(
                name: "p_energy");

            migrationBuilder.DropTable(
                name: "p_inspection");

            migrationBuilder.DropTable(
                name: "p_material");

            migrationBuilder.DropColumn(
                name: "remainQuantity",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "remainEquData",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "remainQuantiy",
                table: "d_energy");

            migrationBuilder.DropColumn(
                name: "remainAttData",
                table: "d_attendance");
        }
    }
}
