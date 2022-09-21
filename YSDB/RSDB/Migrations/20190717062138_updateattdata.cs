using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class updateattdata : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(4627),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(9729));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(2790),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(8019));

            migrationBuilder.AlterColumn<string>(
                name: "equData",
                table: "p_equipment",
                maxLength: 120,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 64);

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(7019),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(2237));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(922),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(6242));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(8952),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(4081));

            migrationBuilder.AlterColumn<string>(
                name: "attData",
                table: "p_attendance",
                maxLength: 120,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 64);

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 16, 0, 0, 0, 0, DateTimeKind.Local));

            migrationBuilder.AlterColumn<string>(
                name: "remainEquData",
                table: "d_equipment",
                maxLength: 120,
                nullable: false,
                defaultValue: "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0",
                oldClrType: typeof(string),
                oldMaxLength: 64,
                oldDefaultValue: "00000000000000000000000000000000000000000000000000000000000000");

            migrationBuilder.AlterColumn<string>(
                name: "equData",
                table: "d_equipment",
                maxLength: 120,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 64);

            migrationBuilder.AlterColumn<string>(
                name: "remainAttData",
                table: "d_attendance",
                maxLength: 120,
                nullable: false,
                defaultValue: "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0",
                oldClrType: typeof(string),
                oldMaxLength: 64,
                oldDefaultValue: "0000000000000000000000000000000");

            migrationBuilder.AlterColumn<string>(
                name: "attData",
                table: "d_attendance",
                maxLength: 120,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 64);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(9729),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(4627));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(8019),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(2790));

            migrationBuilder.AlterColumn<string>(
                name: "equData",
                table: "p_equipment",
                maxLength: 64,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 120);

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(2237),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(7019));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(6242),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(922));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 20, 42, 29, 166, DateTimeKind.Local).AddTicks(4081),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(8952));

            migrationBuilder.AlterColumn<string>(
                name: "attData",
                table: "p_attendance",
                maxLength: 64,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 120);

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 0, 0, 0, 0, DateTimeKind.Local));

            migrationBuilder.AlterColumn<string>(
                name: "remainEquData",
                table: "d_equipment",
                maxLength: 64,
                nullable: false,
                defaultValue: "00000000000000000000000000000000000000000000000000000000000000",
                oldClrType: typeof(string),
                oldMaxLength: 120,
                oldDefaultValue: "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");

            migrationBuilder.AlterColumn<string>(
                name: "equData",
                table: "d_equipment",
                maxLength: 64,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 120);

            migrationBuilder.AlterColumn<string>(
                name: "remainAttData",
                table: "d_attendance",
                maxLength: 64,
                nullable: false,
                defaultValue: "0000000000000000000000000000000",
                oldClrType: typeof(string),
                oldMaxLength: 120,
                oldDefaultValue: "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");

            migrationBuilder.AlterColumn<string>(
                name: "attData",
                table: "d_attendance",
                maxLength: 64,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 120);
        }
    }
}
