using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class summary : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 754, DateTimeKind.Local).AddTicks(4496),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 245, DateTimeKind.Local).AddTicks(57));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(8762),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(8331));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 751, DateTimeKind.Local).AddTicks(8569),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(2577));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(2754),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(6539));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 752, DateTimeKind.Local).AddTicks(6198),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(4574));

            migrationBuilder.AlterColumn<string>(
                name: "accountNumber",
                table: "accountTitle",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "accountName",
                table: "accountTitle",
                maxLength: 200,
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "p_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    rdType = table.Column<byte>(nullable: false),
                    accountNumber = table.Column<string>(maxLength: 50, nullable: false),
                    rdFunds = table.Column<decimal>(type: "decimal(12,2)", nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_summary", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_summary");

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 245, DateTimeKind.Local).AddTicks(57),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 754, DateTimeKind.Local).AddTicks(4496));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(8331),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(8762));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(2577),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 751, DateTimeKind.Local).AddTicks(8569));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(6539),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 753, DateTimeKind.Local).AddTicks(2754));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 19, 11, 0, 57, 244, DateTimeKind.Local).AddTicks(4574),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 19, 13, 59, 37, 752, DateTimeKind.Local).AddTicks(6198));

            migrationBuilder.AlterColumn<string>(
                name: "accountNumber",
                table: "accountTitle",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 50);

            migrationBuilder.AlterColumn<string>(
                name: "accountName",
                table: "accountTitle",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 200);
        }
    }
}
