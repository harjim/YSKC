using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class captt : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 245, DateTimeKind.Local).AddTicks(57),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(9189));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(8331),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(7481));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(2577),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(1701));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(6539),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(5605));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(4574),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(3685));

            migrationBuilder.AlterColumn<string>(
                name: "capital",
                table: "company",
                maxLength: 20,
                nullable: false,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(9189),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 245, DateTimeKind.Local).AddTicks(57));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(7481),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(8331));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(1701),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(2577));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(5605),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(6539));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 8, 44, 5, 420, DateTimeKind.Local).AddTicks(3685),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(4574));

            migrationBuilder.AlterColumn<int>(
                name: "capital",
                table: "company",
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 20);
        }
    }
}
