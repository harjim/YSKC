using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class equdepreciation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(9189),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(8393));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(7481),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(6673));

            migrationBuilder.AddColumn<decimal>(
                name: "depreciation",
                table: "p_equipment",
                type: "decimal(12,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdDepreciation",
                table: "p_equipment",
                type: "decimal(12,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(1701),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(855));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(5605),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(4849));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(3685),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(2964));

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 0, 0, 0, 0, DateTimeKind.Local));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "depreciation",
                table: "p_equipment");

            migrationBuilder.DropColumn(
                name: "rdDepreciation",
                table: "p_equipment");

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(8393),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(9189));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(6673),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(7481));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(855),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(1701));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(4849),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(5605));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(2964),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(3685));

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 0, 0, 0, 0, DateTimeKind.Local));
        }
    }
}
