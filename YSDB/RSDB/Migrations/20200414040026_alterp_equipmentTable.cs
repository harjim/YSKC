using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterp_equipmentTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "depreciation",
                table: "p_equipment");

            migrationBuilder.DropColumn(
                name: "hasModify",
                table: "p_equipment");

            migrationBuilder.DropColumn(
                name: "rdDepreciation",
                table: "p_equipment");

            migrationBuilder.DropColumn(
                name: "rdHour",
                table: "p_equipment");

            migrationBuilder.AlterColumn<string>(
                name: "usedEquData",
                table: "p_equipment_used",
                maxLength: 200,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 120);

            migrationBuilder.AlterColumn<string>(
                name: "equData",
                table: "p_equipment",
                maxLength: 200,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 120);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "usedEquData",
                table: "p_equipment_used",
                maxLength: 120,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 200);

            migrationBuilder.AlterColumn<string>(
                name: "equData",
                table: "p_equipment",
                maxLength: 120,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 200);

            migrationBuilder.AddColumn<decimal>(
                name: "depreciation",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<bool>(
                name: "hasModify",
                table: "p_equipment",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<decimal>(
                name: "rdDepreciation",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "rdHour",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0);
        }
    }
}
