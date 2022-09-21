using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class accountcompany : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<byte>(
                name: "type",
                table: "user",
                nullable: false,
                defaultValue: (byte)0);

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 105, DateTimeKind.Local).AddTicks(3541),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(3630));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(8622),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(1858));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(2910),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(5882));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(3474),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(9762));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(8304),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(7819));

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 18, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 17, 0, 0, 0, 0, DateTimeKind.Local));

            migrationBuilder.AddColumn<byte>(
                name: "from",
                table: "company",
                nullable: false,
                defaultValue: (byte)0);

            migrationBuilder.AddColumn<byte>(
                name: "status",
                table: "company",
                nullable: false,
                defaultValue: (byte)0);

            migrationBuilder.CreateTable(
                name: "accountTitle",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    accountNumber = table.Column<string>(nullable: true),
                    accountName = table.Column<string>(nullable: true),
                    parentId = table.Column<int>(nullable: false),
                    accoutType = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_accountTitle", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "accountTitle");

            migrationBuilder.DropColumn(
                name: "type",
                table: "user");

            migrationBuilder.DropColumn(
                name: "from",
                table: "company");

            migrationBuilder.DropColumn(
                name: "status",
                table: "company");

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_material",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(3630),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 105, DateTimeKind.Local).AddTicks(3541));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_inspection",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 626, DateTimeKind.Local).AddTicks(1858),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(8622));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_energy",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(5882),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(2910));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_design",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(9762),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 104, DateTimeKind.Local).AddTicks(3474));

            migrationBuilder.AlterColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 16, 59, 40, 625, DateTimeKind.Local).AddTicks(7819),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 8, 29, 42, 103, DateTimeKind.Local).AddTicks(8304));

            migrationBuilder.AlterColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 17, 0, 0, 0, 0, DateTimeKind.Local),
                oldClrType: typeof(DateTime),
                oldDefaultValue: new DateTime(2019, 7, 18, 0, 0, 0, 0, DateTimeKind.Local));
        }
    }
}
