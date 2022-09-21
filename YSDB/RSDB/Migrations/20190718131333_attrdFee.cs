using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class attrdFee : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(8393),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 161, DateTimeKind.Local).AddTicks(1265));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(6673),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(7817));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(855),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(171));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(4849),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(4374));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(2964),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(2108));

            migrationBuilder.AddColumn<decimal>(
                name: "insuranceFund",
                table: "p_attendance",
                type: "decimal(10,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "pay",
                table: "p_attendance",
                type: "decimal(10,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdInsuranceFund",
                table: "p_attendance",
                type: "decimal(10,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdPay",
                table: "p_attendance",
                type: "decimal(10,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<string>(
                name: "linkMan",
                table: "company",
                maxLength: 20,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "remark",
                table: "company",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "insuranceFund",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "pay",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdInsuranceFund",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdPay",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "linkMan",
                table: "company");

            migrationBuilder.DropColumn(
                name: "remark",
                table: "company");

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 161, DateTimeKind.Local).AddTicks(1265),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(8393));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(7817),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(6673));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(171),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(855));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(4374),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(4849));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(2108),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 21, 13, 33, 203, DateTimeKind.Local).AddTicks(2964));
        }
    }
}
