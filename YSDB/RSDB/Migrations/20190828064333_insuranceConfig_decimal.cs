using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class insuranceConfig_decimal : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<decimal>(
                name: "unemploymentOfCom",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "unemployment",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "medicalOfCom",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "medical",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "maternityOfCom",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "maternity",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "injuryOfCom",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "injury",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "houseOfCom",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "house",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "endowmentOfCom",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "endowment",
                table: "insuranceConfig",
                type: "decimal(6,4)",
                nullable: false,
                oldClrType: typeof(decimal));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<decimal>(
                name: "unemploymentOfCom",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "unemployment",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "medicalOfCom",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "medical",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "maternityOfCom",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "maternity",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "injuryOfCom",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "injury",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "houseOfCom",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "house",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "endowmentOfCom",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");

            migrationBuilder.AlterColumn<decimal>(
                name: "endowment",
                table: "insuranceConfig",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(6,4)");
        }
    }
}
