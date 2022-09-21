using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class companycodelength : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 445, DateTimeKind.Local).AddTicks(759),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 754, DateTimeKind.Local).AddTicks(4496));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 444, DateTimeKind.Local).AddTicks(7641),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(8762));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 443, DateTimeKind.Local).AddTicks(9497),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 751, DateTimeKind.Local).AddTicks(8569));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 444, DateTimeKind.Local).AddTicks(3781),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(2754));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 444, DateTimeKind.Local).AddTicks(1474),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 752, DateTimeKind.Local).AddTicks(6198));

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 20, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 0, 0, 0, 0, DateTimeKind.Local));

            migrationBuilder.AlterColumn<string>(
                name: "industryCode",
                table: "company",
                maxLength: 30,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 20);

            migrationBuilder.AlterColumn<string>(
                name: "addressCode",
                table: "company",
                maxLength: 30,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 10);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 754, DateTimeKind.Local).AddTicks(4496),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 445, DateTimeKind.Local).AddTicks(759));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(8762),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 444, DateTimeKind.Local).AddTicks(7641));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 751, DateTimeKind.Local).AddTicks(8569),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 443, DateTimeKind.Local).AddTicks(9497));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(2754),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 444, DateTimeKind.Local).AddTicks(3781));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 752, DateTimeKind.Local).AddTicks(6198),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 20, 10, 3, 26, 444, DateTimeKind.Local).AddTicks(1474));

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 20, 0, 0, 0, 0, DateTimeKind.Local));

            migrationBuilder.AlterColumn<string>(
                name: "industryCode",
                table: "company",
                maxLength: 20,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 30);

            migrationBuilder.AlterColumn<string>(
                name: "addressCode",
                table: "company",
                maxLength: 10,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 30);
        }
    }
}
