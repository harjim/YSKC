using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class delRdDeptIdAndEtypeAndDepreciation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "depreciation",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "rdDeptId",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "etype",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "rdDeptId",
                table: "employee");

            migrationBuilder.AlterColumn<int>(
                name: "accountTitleId",
                table: "d_bonus",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "depreciation",
                table: "equipment",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "rdDeptId",
                table: "equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "etype",
                table: "employee",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "rdDeptId",
                table: "employee",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<int>(
                name: "accountTitleId",
                table: "d_bonus",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
