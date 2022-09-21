using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class equproject : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(3630),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(4627));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(1858),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(2790));

            migrationBuilder.AddColumn<int>(
                name: "projectId",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(5882),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(7019));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(9762),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(922));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(7819),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(8952));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "projectId",
                table: "p_equipment");

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(4627),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(3630));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(2790),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(1858));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(7019),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(5882));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 888, DateTimeKind.Local).AddTicks(922),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(9762));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 14, 21, 37, 887, DateTimeKind.Local).AddTicks(8952),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(7819));
        }
    }
}
