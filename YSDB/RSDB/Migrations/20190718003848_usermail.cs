using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class usermail : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "email",
                table: "user",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 161, DateTimeKind.Local).AddTicks(1265),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 105, DateTimeKind.Local).AddTicks(3541));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(7817),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(8622));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(171),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(2910));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(4374),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(3474));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(2108),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(8304));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "email",
                table: "user");

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 105, DateTimeKind.Local).AddTicks(3541),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 161, DateTimeKind.Local).AddTicks(1265));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(8622),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(7817));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(2910),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(171));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(3474),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(4374));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(8304),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 38, 48, 160, DateTimeKind.Local).AddTicks(2108));
        }
    }
}
