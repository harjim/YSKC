using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class pequ : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "user",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "user",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(9729));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(8019));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_inspection",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(2237));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_energy",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(6242));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_design",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(4081));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "company",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "company",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "company",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "company",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "p_equipment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    equipmentDataId = table.Column<int>(nullable: false),
                    equData = table.Column<string>(maxLength: 64, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_equipment", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_equipment");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "user");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "user");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_inspection");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_inspection");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "createTime",
                table: "company");

            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "company");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "company");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "company");
        }
    }
}
